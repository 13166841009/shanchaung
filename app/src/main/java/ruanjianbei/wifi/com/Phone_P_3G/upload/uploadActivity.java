package ruanjianbei.wifi.com.Phone_P_3G.upload;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.luoxudong.app.asynchttp.AsyncHttpRequest;
import com.luoxudong.app.asynchttp.AsyncHttpUtil;
import com.luoxudong.app.asynchttp.callable.SimpleRequestCallable;
import com.luoxudong.app.asynchttp.callable.UploadRequestCallable;
import com.luoxudong.app.asynchttp.model.FileWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ruanjianbei.wifi.com.Phone_P_3G.upload.util.MiSportButton;
import ruanjianbei.wifi.com.Phone_P_3G.upload.util.RoundProgressBarWidthNumber;
import ruanjianbei.wifi.com.Phone_P_3G.upload.util.yashuo;
import ruanjianbei.wifi.com.Phone_P_3G.util.files_delete;
import ruanjianbei.wifi.com.Phone_P_3G.util.get_time;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.my_setting.util.DBServiceOperate;
import ruanjianbei.wifi.com.shanchuang.R;
import util.MyApplication;
import view.TitleBarView;

/**
 * Created by linankun1 on 2016/4/21.
 */
public class uploadActivity extends Activity {
    private ruanjianbei.wifi.com.Phone_P_3G.upload.util.RoundProgressBarWidthNumber mRoundProgressBar;
    private EditText et_filepath;
    private Context context;
    public static final String ZIP_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/shangchuan/zip/";
    private Map<String, FileWrapper> fileWrappers;
    private static final int MSG_HANDLER_MSG = 1;
    private static final int MSG_PROGRESS_UPDATE = 0x110;
    private AsyncHttpRequest request1,request2;

    private static List<String> fileUpload = FragmentChoose.getFileChoose();
    private String filePath = "";
    private ruanjianbei.wifi.com.my_setting.util.DBServiceOperate db_user;
    private ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate db_file;
    private String user_name;
    private boolean sign,sc = false;
    private static String url1 = MyApplication.URL+"/thinkphp/Index/User_is_regedit";
    private static String url2 = MyApplication.URL+"/thinkphp/Files/Files_Android";

    private TitleBarView mtitlrbar;

    private List<String> fileNames = new ArrayList<String>();

    private MiSportButton mBtn;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(uploadActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    //将所存储选择项的集合清空
                    FragmentChoose.setFileChoose(null);
                    //删除压缩文件
                    files_delete files = new files_delete(ZIP_PATH);
                    files.deleteAll();
                    uploadActivity.this.finish();
                    break;
                case 2:
                    int nowProgress = Integer.parseInt(String.valueOf(msg.obj));
                    mRoundProgressBar.setProgress(nowProgress);
                    int roundProgress = mRoundProgressBar.getProgress();
                    Log.i("记录进度：", "" + nowProgress+"辅："+roundProgress);
                    if (roundProgress >= 100) {
                        mRoundProgressBar.setProgress(0);
//                        mHandler.removeMessages(MSG_PROGRESS_UPDATE);
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
        mtitlrbar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlrbar.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        mtitlrbar.setTitleText(R.string.wifipostTitle);
        mtitlrbar.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        mtitlrbar.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadActivity.this.finish();
            }
        });

        /**
         * 将已选文件设置到此处
         */
        for (String s : fileUpload) {
            /**
             * 获取文件名
             */
            int start = s.lastIndexOf("/");
            int end = s.lastIndexOf(".");
            if(start!=-1&&end!=-1){
                fileNames.add(s.substring(start+1,end));
            }else{
                fileNames.add(null);
            }
            //所有已选文件的路径
            filePath += s + ";";
        }

        mRoundProgressBar = (RoundProgressBarWidthNumber) findViewById(R.id.pro);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);
        mBtn = (MiSportButton) findViewById(R.id.mi_btn);
        //      读取本地用户
        if(!ReadUser()){
            Toast.makeText(uploadActivity.this, "请登录后使用该功能", Toast.LENGTH_SHORT).show();
            sc=true;
        }
        mBtn.setMiSportBtnClickListener(new MiSportButton.miSportButtonClickListener() {
            @Override
            public void finishClick() {
                String filespath = null;
                if(sc){
                    Toast.makeText(uploadActivity.this, "请登录后使用该功能", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断文件选择是否为空
                if (!filePath.equals("")) {
                    filespath = filePath;
                } else {
                    Toast.makeText(context, "your choose files is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                // 判断是否输入用户名
                if("".equals(et_filepath.getText().toString())){
                    Toast.makeText(context, "please input toUserName", Toast.LENGTH_LONG).show();
                    return;
                }
                //用户识别是否存在
                 UserCheck(filespath);
            }
            @Override
            public void continueClick() {
                if(request1!=null&&request2!=null){
                    request1.cancel();
                    request2.cancel();
                    uploadActivity.this.finish();
                }else{
                    uploadActivity.this.finish();
                }
            }
        });

        mBtn.setMiSportBtnLongClickListener(new MiSportButton.miSportButtonLongClickListener() {
            @Override
            public void longPressClick() {

            }
        });
    }

    private boolean ReadUser(){
        //读取用户名
        db_user = new DBServiceOperate(uploadActivity.this);
        Cursor cursor =  db_user.selectInformation();
        if (cursor != null&&cursor.getCount()!=0) {
            if (cursor.moveToFirst()) {//just need to query one time
                user_name = cursor.getString(cursor.getColumnIndex("Name"));
            }
            if(!"".equals(user_name+"")){
                return true;
            }
        }
        cursor.close();
        return false;
    }
    private void FileZip(String filespath){
        fileWrappers = new HashMap<String, FileWrapper>();//初始化Map集合
        //转换多文件路径
        String str[] = filedo(filespath);
        for (int x = 0; x < str.length; x++) {
            //获取文件全名，包括后缀
            String[] str1 = str[x].split("/");
            String file_all_name = str1[str1.length - 1];
            Log.i("wenjian:", file_all_name);
            //获取文件名
            String file_name = file_all_name.substring(0, file_all_name.lastIndexOf("."));
            File fileinfo = new File(str[x]);
            if (!fileinfo.exists()) {
                Toast.makeText(context, "文件:" + file_all_name + "不存在", Toast.LENGTH_LONG).show();
                return;
            }
            //对文件名进行URL转码
            String file_name_encode = url(file_name);
            //将文件进行压缩
            //Toast.makeText(context, "file:"+file_all_name+" is compressing!", Toast.LENGTH_LONG).show();
            String zipload = ZIP_PATH + file_name_encode + ".zip";
            yashuo ys = new yashuo();
            try {
                ys.zip(zipload, new File(str[x]));
            } catch (Exception e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            //Toast.makeText(context, "file:"+file_all_name+" compress complete!", Toast.LENGTH_LONG).show();
            Log.i("文件路径/压缩路径", zipload);
            File files = new File(zipload);
            if (files.exists()) {
                //定义一个file容器，添加文件
                FileWrapper file = new FileWrapper();
                file.setFile(new File(zipload));
                fileWrappers.put(file_all_name, file);
                Log.i("文件路径" + x, str[x]);
            } else {
                Toast.makeText(context, "源压缩文件:" + file_name_encode + "不存在", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }
    private void UserCheck(final String filespath){
        request1 = new AsyncHttpUtil.Builder()
                .url(url1)
                .addFormData("toman", et_filepath.getText().toString())//设置form表单数据，也可以调用setFormDatas方法
                .setCallable(new SimpleRequestCallable() {
                    @Override
                    public void onFailed(int errorCode, String errorMsg) {
                        Toast.makeText(uploadActivity.this, "网络连接错误",
                                Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onSuccess(String responseInfo) {
                        try {
                            Toast.makeText(uploadActivity.this, "后台压缩中...", Toast.LENGTH_SHORT).show();
                            sign=true;
                            JSONObject ob = new JSONObject(responseInfo);
                            if(ob.getString("returncode").equals("1")){
                                //文件解压
                                Log.i("file:",filespath);
                                FileZip(filespath);
                                //文件上传
                                Fileupload();
                            }else{
                                Toast.makeText(uploadActivity.this, "你传输的对象不存在，请注册", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).build().post();
    }
    private void Fileupload(){
        //联网上传文件
        request2 = new AsyncHttpUtil.Builder()
                .url(url2)
                //.addUploadFile("file1", new File("/storage/sdcard1/IMG_20160318_202540_HDR.jpg"))
                .setFileWrappers(fileWrappers)
                .addFormData("Postman", user_name)
                .addFormData("Toman", et_filepath.getText().toString())
                //.addFormData("Filename", filename)//添加form参数
                .setCallable(new UploadRequestCallable() {
                    @Override
                    public void onFailed(int errorCode, String errorMsg) {
                        //上传失败
                        Log.i("文件上传中:",""+errorCode+" "+errorMsg);
                        Message message = new Message();
                        message.what = MSG_HANDLER_MSG;
                        message.obj = "上传失败：" + errorMsg;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onTransfering(String name, long totalLength, long transferedLength) {
                        //上传进度
                        Message message = new Message();
                        message.what = MSG_HANDLER_MSG + 1;
                        //message.obj = "上传进度：" + name + ">>>" + totalLength + ">>>" + transferedLength;
                        message.obj = transferedLength * 100 / totalLength;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onTransferSuc(String name) {
                        //文件name上传完成
                        Message message = new Message();
                        message.what = MSG_HANDLER_MSG;
                        message.obj = "文件:" + name + " 上传完成";
                        mHandler.sendMessage(message);
                        //保存信息到本地数据库
                        db_file =new ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate(uploadActivity.this);
                        db_file.saveInformation(name, new get_time().getTime(), "上传");
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
    }

    private String[] filedo(String files) {
        String str[] = null;
        str = files.split(";");
        return str;
    }

    private String url(String file_name) {
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
