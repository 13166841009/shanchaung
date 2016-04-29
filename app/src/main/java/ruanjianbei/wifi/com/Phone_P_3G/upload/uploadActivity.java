package ruanjianbei.wifi.com.Phone_P_3G.upload;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.luoxudong.app.asynchttp.AsyncHttpRequest;
import com.luoxudong.app.asynchttp.AsyncHttpUtil;
import com.luoxudong.app.asynchttp.callable.UploadRequestCallable;
import com.luoxudong.app.asynchttp.model.FileWrapper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ruanjianbei.wifi.com.Phone_P_3G.upload.util.RoundProgressBarWidthNumber;
import ruanjianbei.wifi.com.Phone_P_3G.upload.util.yashuo;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/4/21.
 */
public class uploadActivity extends Activity {
    private ruanjianbei.wifi.com.Phone_P_3G.upload.util.HorizontalProgressBarWithNumber mProgressBar;
    private ruanjianbei.wifi.com.Phone_P_3G.upload.util.RoundProgressBarWidthNumber mRoundProgressBar;
    private EditText et_filepath;
    private Context context;
    public static final String ZIP_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/zip/";
    private String URL = "http://zh749931552.6655.la/ThinkPHP/index.php/Files/Files_Android";
    private Map<String, FileWrapper> fileWrappers ;
    private static final int MSG_HANDLER_MSG = 1;
    private static final int MSG_PROGRESS_UPDATE = 0x110;
    private AsyncHttpRequest request = null;

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(uploadActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    int roundProgress = mRoundProgressBar.getProgress();
                    //mProgressBar.setProgress(++progress);
                    mRoundProgressBar.setProgress(Integer.parseInt(String.valueOf(msg.obj)));
                    Log.i("记录", ""+Integer.parseInt(String.valueOf(msg.obj)));
                    if (roundProgress >= 100) {
                        mHandler.removeMessages(MSG_PROGRESS_UPDATE);
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE, 100);//设置更新时间ms
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_upload_3g);
        et_filepath = (EditText) findViewById(R.id.et_filepath);
        mRoundProgressBar = (RoundProgressBarWidthNumber) findViewById(R.id.pro);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload:
                fileWrappers = new HashMap<String, FileWrapper>();//初始化Map集合
                String filespath = null;
                //判断文件选择是否为空
                if(!"".equals(String.valueOf(et_filepath.getText()))){
                    filespath = et_filepath.getText().toString();
                }else{
                    Toast.makeText(context, "your choose files is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                //转换多文件路径
                String str[] = filedo(filespath);
                for(int x=0;x<str.length;x++){
                    //获取文件全名，包括后缀
                    String[] str1 = str[x].split("/");
                    String file_all_name = str1[str1.length-1];
                    //获取文件名
                    String file_name = file_all_name.substring(0,file_all_name.lastIndexOf("."));
                    File fileinfo = new File(str[x]);
                    if (!fileinfo.exists()) {
                        Toast.makeText(context, "file:"+file_all_name+" not exists", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //对文件名进行URL转码
                    String file_name_encode = url(file_name);
                    //将文件进行压缩
                    String zipload = ZIP_PATH+file_name_encode+".zip";
                    yashuo ys = new yashuo();
                    try {
                        ys.zip(zipload,new File(str[x]));
                    } catch (Exception e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    Log.i("文件路径/压缩路径", zipload);
                    File files = new File(zipload);
                    if (files.exists()) {
                        //定义一个file容器，添加文件
                        FileWrapper file = new FileWrapper();
                        file.setFile(new File(zipload));
                        fileWrappers.put(file_all_name, file);
                        Log.i("文件路径"+x,str[x]);
                    }else
                    {
                        Toast.makeText(context, "zipfile:"+file_name_encode+" not exists", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                request = new AsyncHttpUtil.Builder()
                        .url(URL)
                                //.addUploadFile("file1", new File("/storage/sdcard1/IMG_20160318_202540_HDR.jpg"))
                        .setFileWrappers(fileWrappers)
                        .addFormData("Postman", "zhanghang")
                        .addFormData("Toman", "linankun")
                                //.addFormData("Filename", filename)//添加form参数
                        .setCallable(new UploadRequestCallable() {
                            @Override
                            public void onFailed(int errorCode, String errorMsg) {
                                //上传失败
                                Message message = new Message();
                                message.what = MSG_HANDLER_MSG;
                                message.obj = "上传失败：" + errorMsg;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void onTransfering(String name, long totalLength, long transferedLength) {
                                //上传进度
                                Message message = new Message();
                                message.what = MSG_HANDLER_MSG+1;
                                //message.obj = "上传进度：" + name + ">>>" + totalLength + ">>>" + transferedLength;
                                message.obj = transferedLength*100/totalLength;
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void onTransferSuc(String name) {
                                //文件name上传完成
                                Message message = new Message();
                                message.what = MSG_HANDLER_MSG;
                                message.obj = "文件:" + name + " 上传完成";
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void onSuccess(String responseInfo) {
                                //全部上传成功！
                                Message message = new Message();
                                message.what = MSG_HANDLER_MSG;
                                message.obj = "全部文件上传成功！";
                                mHandler.sendMessage(message);
                            }

                            @Override
                            public void onCancel() {
                                //上传取消
                                Message message = new Message();
                                message.what = MSG_HANDLER_MSG;
                                message.obj = "上传取消";
                                mHandler.sendMessage(message);
                            }
                        })
                        .build().upload();
                break;
            case R.id.btn_cancle:
                request.cancel();
                break;
        }

    }
    private String[] filedo(String files){
        String str[] = null;
        str = files.split(";");
        return str;
    }
    private String url(String file_name){
        String file_name_encoder = null;
        try {
            file_name_encoder = URLEncoder.encode(file_name, "UTF-8");
            //file_name_encoder = URLDecoder.decode(file_name, "UTF-8");
            file_name_encoder = file_name_encoder.replace("+", "%20");
            //2014%20-%20%E5%89%AF%E6%9C%AC
        } catch (UnsupportedEncodingException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return file_name_encoder;
    }
}
