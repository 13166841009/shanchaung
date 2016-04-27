package ruanjianbei.wifi.com.Phone_P_3G.download.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import ruanjianbei.wifi.com.Phone_P_3G.download.entities.FileInfo;

/**
 * Created by linankun1 on 2016/4/23.
 */
public class DownloadService extends Service
{
    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/zip/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";// 结束下载任务
    public static final int MSG_INIT = 0x1;
    private InitThread mInitThread = null;
    private Map<Integer, DownloadTask> mTasks = new LinkedHashMap<Integer, DownloadTask>();

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    /**
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // 获得Activity传过来的参数
        if (ACTION_START.equals(intent.getAction()))
        {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            // 启动初始化线程
            mInitThread = new InitThread(fileInfo);
//			mInitThread.start();
            DownloadTask.sExecutorService.execute(mInitThread);
        }
        else if (ACTION_STOP.equals(intent.getAction()))
        {
            //暂停下载
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            //从集合中取出下载任务
            DownloadTask task = mTasks.get(fileInfo.getId());
            if (task != null) {
                // 设置下载线程处于暂停状态
                task.isPause = true;
            }

        }

        return super.onStartCommand(intent, flags, startId);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    // 启动下载任务
                    DownloadTask mTask = new DownloadTask(DownloadService.this, fileInfo,3);
                    mTask.downLoad();
                    // 将下载任务添加到集合中
                    mTasks.put(fileInfo.getId(), mTask);
                    break;

                default:
                    break;
            }
        };
    };

    private class InitThread extends Thread
    {
        private FileInfo mFileInfo = null;

        public InitThread(FileInfo mFileInfo)
        {
            this.mFileInfo = mFileInfo;
        }

        /**
         * @see java.lang.Thread#run()
         */
        @Override
        public void run()
        {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;

            try
            {
                // 连接网络文件
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int length = -1;

                if (connection.getResponseCode() == HttpStatus.SC_OK)
                {
                    // 获得文件的长度
                    length = connection.getContentLength();
                }

                if (length <= 0)
                {
                    return;
                }

                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists())
                {
                    dir.mkdir();
                }

                // 在本地创建文件
                File file = new File(dir, mFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                // 设置文件长度
                raf.setLength(length);
                mFileInfo.setLength(length);
                mHandler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (connection != null)
                {
                    connection.disconnect();
                }
                if (raf != null)
                {
                    try
                    {
                        raf.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
