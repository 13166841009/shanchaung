package ruanjianbei.wifi.com.Phone_P_3G.download.db;

import java.util.List;

import ruanjianbei.wifi.com.Phone_P_3G.download.entities.ThreadInfo;

/**
 * Created by linankun1 on 2016/4/23.
 */
public interface ThreadDAO
{
    /**
     * 插入线程信息
     * @param threadInfo
     * @return void
     * @author Yann
     * @date 2015-8-8 上午10:56:18
     */
    public void insertThread(ThreadInfo threadInfo);
    /**
     * 删除线程信息
     * @param url
     * @param thread_id
     * @return void
     * @author Yann
     * @date 2015-8-8 上午10:56:57
     */
    public void deleteThread(String url);
    /**
     * 更新线程下载进度
     * @param url
     * @param thread_id
     * @return void
     * @author Yann
     * @date 2015-8-8 上午10:57:37
     */
    public void updateThread(String url, int thread_id, int finished);
    /**
     * 查询文件的线程信息
     * @param url
     * @return
     * @return List<ThreadInfo>
     * @author Yann
     * @date 2015-8-8 上午10:58:48
     */
    public List<ThreadInfo> getThreads(String url);
    /**
     * 线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     * @return boolean
     * @author Yann
     * @date 2015-8-8 上午10:59:41
     */
    public boolean isExists(String url, int thread_id);
}
