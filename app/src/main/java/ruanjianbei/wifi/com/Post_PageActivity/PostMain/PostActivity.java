package ruanjianbei.wifi.com.Post_PageActivity.PostMain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.library.ComboView;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Phone_P_3G.upload.uploadActivity;
import ruanjianbei.wifi.com.Post_PageActivity.Android_postActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiRippleOutLayout;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.Wifistatus;
import ruanjianbei.wifi.com.ScanActivity.ScanningActivity;
import ruanjianbei.wifi.com.Utils.GetFilsSize;
import ruanjianbei.wifi.com.Utils.WifiConnect.WifiCheck;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.dialog.CustomDialog;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

import static ruanjianbei.wifi.com.shanchuang.R.id.postChoose;

public class PostActivity extends Activity {
    /**
     * 数据连接
     */
    private TextView filesize;
    //当前传输文件大小
    private String size;
    private List<String> filesizelist = FragmentChoose.getFileChoose();
    Class telephonyManagerClass;
    Object ITelephonyStub;
    Class ITelephonyClass;
    TelephonyManager telephonyManager;
    //判断当前网络是否可以访问internet
    private Boolean ifconnect;
    //wifi网卡操作
    private WifiAdmin wifiadmin;
    //获取wifi操作的实例
    private WifiCheck wifiCheck;
    private TitleBarView mtitlrbar;
    /**
     * 进入后进行文件传输存在bug
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_post);
        initTitle();
        telephonyManager = (TelephonyManager) getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        wifiadmin = new WifiAdmin(PostActivity.this);
        wifiCheck = new WifiCheck(this);
        /**
         * 数据连接
         */
        try {
            telephonyManagerClass = Class.forName(telephonyManager
                    .getClass().getName());
            Method getITelephonyMethod = telephonyManagerClass
                    .getDeclaredMethod("getITelephony");
            getITelephonyMethod.setAccessible(true);
            ITelephonyStub = getITelephonyMethod
                    .invoke(telephonyManager);
            ITelephonyClass = Class.forName(ITelephonyStub.getClass()
                    .getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ComboView android = (ComboView) findViewById(R.id.android_receive);
        ComboView ios = (ComboView) findViewById(R.id.ios_receive);
        ComboView.Params params = ComboView.Params.create()
                //Android文件传输
                .cornerRadius(dimen(R.dimen.cb_dimen_25), dimen(R.dimen.cb_dimen_52))// Following three to***** values must be the same can morph to circle
                .width(dimen(R.dimen.cb_dimen_70), dimen(R.dimen.cb_dimen_52))
                .height(dimen(R.dimen.cb_dimen_38), dimen(R.dimen.cb_dimen_52))
                .morphDuration(300)
                .text("安卓文件传输", "点击进入")
                        //Option -- and values below is default
                .color(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .colorPressed(color(R.color.cb_color_blue_dark), color(R.color.cb_color_blue_dark))
                .strokeWidth(dimen(R.dimen.cb_dimen_1), dimen(R.dimen.cb_dimen_1))
                .strokeColor(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .circleDuration(3000)
                .rippleDuration(200)
                .padding(dimen(R.dimen.cb_dimen_3))
                .textSize(16)
                .textColor(color(R.color.cb_color_white))
                .comboClickListener(new ComboView.ComboClickListener() {
                    @Override
                    public void onComboClick() {
                        checkNetWork();
                        // startActivity(new Intent(PostActivity.this, Android_postActivity.class));
                    }

                    @Override
                    public void onNormalClick() {
                    }
                });
        ComboView.Params iosparams = ComboView.Params.create()

                //Android文件传输
                .cornerRadius(dimen(R.dimen.cb_dimen_25), dimen(R.dimen.cb_dimen_52))// Following three to***** values must be the same can morph to circle
                .width(dimen(R.dimen.cb_dimen_70), dimen(R.dimen.cb_dimen_52))
                .height(dimen(R.dimen.cb_dimen_38), dimen(R.dimen.cb_dimen_52))
                .morphDuration(300)
                .text("苹果文件传输", "点击进入")
                        //Option -- and values below is default
                .color(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .colorPressed(color(R.color.cb_color_blue_dark), color(R.color.cb_color_blue_dark))
                .strokeWidth(dimen(R.dimen.cb_dimen_1), dimen(R.dimen.cb_dimen_1))
                .strokeColor(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .circleDuration(3000)
                .rippleDuration(200)
                .padding(dimen(R.dimen.cb_dimen_3))
                .textSize(16)
                .textColor(color(R.color.cb_color_white))
                .comboClickListener(new ComboView.ComboClickListener() {
                    @Override
                    public void onComboClick() {
                        Intent intent = new Intent();
                        //1表示发送
                        intent.putExtra("type", "1");
                        intent.setClass(PostActivity.this, ScanningActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onNormalClick() {
                    }
                });
        android.settingMorphParams(params);
        ios.settingMorphParams(iosparams);
    }
    private  void initTitle(){
        long fileall = 0;
        filesize = (TextView) findViewById(R.id.postfilesize);
        for(int i =0 ;i<filesizelist.size();i++){
            try {
                fileall += GetFilsSize.getFileSizes(new File(filesizelist.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        size = GetFilsSize.FormentFileSize(fileall);
        filesize.setText(filesize.getText().toString()+":"+filesizelist.size()+"个"+" "+"("+size+")");
        mtitlrbar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlrbar.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.VISIBLE);
        mtitlrbar.setTitleText(R.string.wifipostTitle);
    }
    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkNetWork() {

        String checkUrl = "https://www.baidu.com/index.html";

        int timeoutDurationConn  = 3;

        Looper mainLooper = Looper.getMainLooper();
        CheckWebserver_Handler handler = new CheckWebserver_Handler(mainLooper);

        new Wifistatus(checkUrl, timeoutDurationConn, handler);
    }


    private class CheckWebserver_Handler extends Handler {

        public CheckWebserver_Handler(Looper mainLooper) {
            super(mainLooper);
            // TODO Auto-generated constructor stub
        }

        public void handleMessage(Message msg) {


            switch (msg.what) {
                case 0: // 网络连接成功
                {
                    if((wifiCheck.isNetworkAvailable())||wifiCheck.is3G()){
                        Toast.makeText(PostActivity.this,"您将选择有网传递",Toast.LENGTH_LONG).show();
                        final CustomDialog dialog = new CustomDialog("网络类型选择",PostActivity.this,
                                R.style.dialogstyle,"您当前传输文件大小为"+size, R.layout.custom_dialog_update);
                        dialog.setOnOkClickListener(new CustomDialog.OnCustomClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog) {
                                wifiadmin.closeNetCard();
                                Intent intent = new Intent(PostActivity.this, uploadActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnCancleClickListener(new CustomDialog.OnCustomClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog) {
                                Intent intent = new Intent(PostActivity.this, uploadActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
                break;
                case 1:  //网络未准备好
                {
                    /**
                     * 扫描当前热点
                     */
                    //断开当前连接热点
                    wifiadmin.closeNetCard();
                    Toast.makeText(PostActivity.this, "您将连接热点进行发送...", Toast.LENGTH_LONG).show();
                    PostActivity.this.finish();
                    startActivity(new Intent(PostActivity.this,Android_postActivity.class));
                }
                //此处可以进行重试处理
                break;
            }
        }
    }
}


