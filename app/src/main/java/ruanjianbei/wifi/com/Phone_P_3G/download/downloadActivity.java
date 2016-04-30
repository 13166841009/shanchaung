package ruanjianbei.wifi.com.Phone_P_3G.download;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.luoxudong.app.asynchttp.AsyncHttpRequest;
import com.luoxudong.app.asynchttp.AsyncHttpUtil;
import com.luoxudong.app.asynchttp.callable.SimpleRequestCallable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruanjianbei.wifi.com.Phone_P_3G.download.Service.DownloadService;
import ruanjianbei.wifi.com.Phone_P_3G.download.entities.FileInfo;
import ruanjianbei.wifi.com.Phone_P_3G.download.zipper.zip;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.FragmentApplication;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/4/23.
 */
public class downloadActivity extends Activity {

    public static downloadActivity mMainActivity = null;
    private ListView mListView = null;
    private List<FileInfo> mFileList = null;
    private FileListAdapter mAdapter = null;
    private TextView textView = null;
    private Map<String, String> file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_main);
        textView = (TextView) findViewById(R.id.textView1);
        mMainActivity = this;
    }

    public void onClick(View v) {
        file = new HashMap<String, String>();
        String toman = textView.getText().toString();
        AsyncHttpRequest request = new AsyncHttpUtil.Builder()
                .url("http://zh749931552.6655.la/ThinkPHP/index.php/Files/Files_Get")
                .addFormData("Toman", toman)//设置form表单数据，也可以调用setFormDatas方法
                .setCallable(new SimpleRequestCallable() {
                    @Override
                    public void onFailed(int errorCode, String errorMsg) {
                        Toast.makeText(mMainActivity, "查询失败，请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String responseInfo) {
                        try {
                            //JSONObject information = new JSONObject(responseInfo);
                            JSONArray numberList = new JSONArray(responseInfo);
                            for (int i = 0; i < numberList.length(); i++) {
                                String file_name = numberList.getJSONObject(i).getString("file_name");
                                String file_load = numberList.getJSONObject(i).getString("load");
                                file.put(file_name, file_load);
                                //Log.i("数据",file_name+" "+file_load);
                            }
                        } catch (JSONException e) {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }
                        //创建文件集合
                        mFileList = new ArrayList<FileInfo>();
                        mListView = (ListView) findViewById(R.id.lv_file);
                        int count = 0;//计算器
                        for (Map.Entry<String, String> entry : file.entrySet()) {
                            String zipname = entry.getKey().substring(0, entry.getKey().lastIndexOf(".")) + ".zip";
                            FileInfo fileInfo = new FileInfo(count, entry.getValue(), zipname, entry.getKey(), 0, 0);
                            mFileList.add(fileInfo);
                            count++;
                            Log.i("数据", entry.getKey() + " " + entry.getValue());
                        }
                        //创建适配器
                        mAdapter = new FileListAdapter(mMainActivity, mFileList);
                        //设置ListView
                        mListView.setAdapter(mAdapter);
                        // 注册广播接收器
                        IntentFilter filter = new IntentFilter();
                        filter.addAction(DownloadService.ACTION_UPDATE);
                        filter.addAction(DownloadService.ACTION_FINISH);
                        registerReceiver(mReceiver, filter);
                    }
                })
                .build().post();
    }

    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mReceiver);
        try {
            unregisterReceiver(mReceiver);//重复注销广播处理函数是会报错，而且会让进程奔溃。可以用使用try catch方式捕获错误
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("Receiver not registered")) {
                // Ignore this exception. This is exactly what is desired
            } else {
                // unexpected, re-throw
                throw e;
            }
        }
    }
    /*public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            //do something here
            Intent intent = new Intent(downloadActivity.this, FragmentApplication.class);
            startActivity(intent);
            return false;//如果是true则屏蔽后退键
        }
        //return true;
        return super.onKeyDown(keyCode, event);
    }*/

    /**
     * 更新UI的广播接收器1
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                //更新进度条
                int finished = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                mAdapter.updateProgress(id, finished);
                Log.i("mReceiver", id + "-finished = " + finished);
            } else if (DownloadService.ACTION_FINISH.equals(intent.getAction())) {
                //下载结束
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                //解压
                String str = fileInfo.getFileName();
                zip z = new zip();
                String str1 = DownloadService.DOWNLOAD_PATH + str;
                Log.i("输出：", str1);
                z.jieya(str1);
                // 当任务下载完毕，进度条设置为0
                mAdapter.updateProgress(fileInfo.getId(), 0);
                Toast.makeText(downloadActivity.this,
                        mFileList.get(fileInfo.getId()).gettrueName() + " 下载完成",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}