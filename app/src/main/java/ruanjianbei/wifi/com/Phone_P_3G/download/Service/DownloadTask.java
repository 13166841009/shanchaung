package ruanjianbei.wifi.com.Phone_P_3G.download.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ruanjianbei.wifi.com.Phone_P_3G.download.db.ThreadDAO;
import ruanjianbei.wifi.com.Phone_P_3G.download.db.ThreadDAOImpl;
import ruanjianbei.wifi.com.Phone_P_3G.download.entities.FileInfo;
import ruanjianbei.wifi.com.Phone_P_3G.download.entities.ThreadInfo;

/**
 * Created by linankun1 on 2016/4/23.
 */
public class DownloadTask
{
    private Context mContext = null;
    private FileInfo mFileInfo = null;
    private ThreadDAO mDao = null;
    private int mFinished = 0;
    public boolean isPause = false;
    private int mThreadCount = 1;//线程数量
    private List<DownloadThread> mThreadList;// 线程集合
    public static ExecutorService sExecutorService = Executors.newCachedThreadPool();//线程池
    /**
     *@param mContext
     *@param mFileInfo
     */
    public DownloadTask(Context mContext, FileInfo mFileInfo,int mThreadCount)
    {
        this.mContext = mContext;
        this.mFileInfo = mFileInfo;
        this.mThreadCount = mThreadCount;
        mDao = new ThreadDAOImpl(mContext);
    }
    public void downLoad()
    {
        // 读取数据库的线程信息
        List<ThreadInfo> threads = mDao.getThreads(mFileInfo.getUrl());
        if (threads.size() == 0){
            // 获取每个线程现在的长度
            int lengthTh = mFileInfo.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                //创建线程信息
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(),
                        i * lengthTh, (i + 1) * lengthTh - 1,0);
                if (i == mThreadCount - 1) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                // 添加到线程信息集合中
                threads.add(threadInfo);
                // 向数据库插入线程信息
                mDao.insertThread(threadInfo);
            }
        }
        mThreadList = new ArrayList<DownloadThread>();
        for (ThreadInfo info : threads) {
            DownloadThread thread = new DownloadThread(info);
//			thread.start();
            DownloadTask.sExecutorService.execute(thread);
            // 添加到线程信息集合中
            mThreadList.add(thread);

        }
    }
    /**
     * 判断所有线程是否都已经执行完毕
     *
     * @return
     */
    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;
        //遍历线程集合，判断线程是否执行完毕
        for (DownloadThread thread : mThreadList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }
        if(allFinished){
            // 删除线程信息
            mDao.deleteThread(mFileInfo.getUrl());
            // 通知广播UI下载任务结束
            Intent intent = new Intent(DownloadService.ACTION_FINISH);
            intent.putExtra("fileInfo", mFileInfo);
            mContext.sendBroadcast(intent);
        }

    }
    /**
     * 下载线程
     * @author Yann
     * @date 2015-8-8 上午11:18:55
     */
    private class DownloadThread extends Thread
    {
        private ThreadInfo mThreadInfo = null;
        private boolean isFinished = false;// 当前线程是否下载结束
        /**
         *@param
         */
        public DownloadThread(ThreadInfo threadInfo)
        {
            mThreadInfo = threadInfo;
        }

        /**
         * @see java.lang.Thread#run()
         */
        @Override
        public void run()
        {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;
            try
            {
                URL url = new URL(mThreadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置下载位置
                int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                connection.setRequestProperty("Range",
                        "bytes=" + start + "-" + mThreadInfo.getEnd());
                // 设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH,
                        mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent();
                intent.setAction(DownloadService.ACTION_UPDATE);
                mFinished += mThreadInfo.getFinished();
                Log.i("mFinised", mThreadInfo.getId() + "finished = " + mThreadInfo.getFinished());
                // 开始下载
                if (connection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT)
                {
                    // 读取数据
                    inputStream = connection.getInputStream();
                    byte buffer[] = new byte[1024 << 2];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1)
                    {
                        // 写入文件
                        raf.write(buffer, 0, len);
                        // 整个文件的完成进度
                        mFinished += len;
                        // 当前线程完成的进度
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + len);
                        mFinished += len;
                        if (System.currentTimeMillis() - time > 100)
                        {
                            time = System.currentTimeMillis();
                            int f = mFinished * 100 / mFileInfo.getLength();
                            if (f > mFileInfo.getFinished())
                            {
                                intent.putExtra("finished", f);
                                intent.putExtra("id", mFileInfo.getId());
                                mContext.sendBroadcast(intent);
                            }
                        }

                        // 在下载暂停时，保存下载进度
                        if (isPause)
                        {
                            mDao.updateThread(mThreadInfo.getUrl(),	mThreadInfo.getId(),
                                    mThreadInfo.getFinished());
                            return;
                        }
                    }
                    // 标识当前线程执行完毕
                    isFinished = true;
                    // 检查下载任务是否执行完毕
                    checkAllThreadFinished();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if (connection != null)
                    {
                        connection.disconnect();
                    }
                    if (raf != null)
                    {
                        raf.close();
                    }
                    if (inputStream != null)
                    {
                        inputStream.close();
                    }
                }
                catch (Exception e2)
                {
                    e2.printStackTrace();
                }
            }
        }
    }

}
