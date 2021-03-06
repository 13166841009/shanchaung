package ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain;

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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.bruce.library.ComboView;

import java.lang.reflect.Method;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.Android_receiveActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.Ios_recevieActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.RecevieByWifi;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.Wifistatus;
import ruanjianbei.wifi.com.ScanActivity.ScanningActivity;
import ruanjianbei.wifi.com.Utils.WifiConnect.WifiCheck;
import ruanjianbei.wifi.com.ViewPagerinfo.MainPageActivity;
import ruanjianbei.wifi.com.dialog.CustomDialog;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class ReceiveActivity extends Activity {
    /**
     * 数据连接
     */
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
    private TitleBarView mtitlebar;
    /**
     * 进入后进行文件传输存在bug
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        initTitle();
        telephonyManager = (TelephonyManager) getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        wifiadmin = new WifiAdmin(ReceiveActivity.this);
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
                .text("安卓文件接收", "点击进入")
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
                        /**
                         * 对当前网络添加判断
                         */
                        checkNetWork();
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
                .text("苹果文件接收", "点击进入")
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
                        intent.putExtra("type", "0");
                        //1表示发送
                        intent.setClass(ReceiveActivity.this, ScanningActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onNormalClick() {
                    }
                });
        android.settingMorphParams(params);
        ios.settingMorphParams(iosparams);
    }

    private void initTitle() {
        mtitlebar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlebar.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mtitlebar.setTitleText(R.string.receivetitle);
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
                        Toast.makeText(ReceiveActivity.this,"您将选择有网接收",Toast.LENGTH_LONG).show();
                        final CustomDialog dialog = new CustomDialog("网络类型选择",ReceiveActivity.this,
                                R.style.dialogstyle, "请选择接收方式",R.layout.custom_dialog_update);
                        dialog.setOnOkClickListener(new CustomDialog.OnCustomClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog) {
                                wifiadmin.closeNetCard();
                                Intent intent = new Intent(ReceiveActivity.this, downloadActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnCancleClickListener(new CustomDialog.OnCustomClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog) {
                                Intent intent = new Intent(ReceiveActivity.this, downloadActivity.class);
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
                     * 建立wifi热点进行连接
                     */
                    Toast.makeText(ReceiveActivity.this, "您将建立热带点进行连接", Toast.LENGTH_LONG).show();
                    wifiadmin.closeNetCard();
                    ReceiveActivity.this.finish();
                    startActivity(new Intent(ReceiveActivity.this,Android_receiveActivity.class));
                }
                //此处可以进行重试处理
                break;
            }
        }
    }
}

