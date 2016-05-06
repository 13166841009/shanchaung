package ruanjianbei.wifi.com.Recevie_PageActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.Wifistatus;
import ruanjianbei.wifi.com.Utils.WifiConnect.WifiCheck;
import ruanjianbei.wifi.com.dialog.CustomDialog;
import ruanjianbei.wifi.com.shanchuang.R;

public class Android_receiveActivity extends Activity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_receive);telephonyManager = (TelephonyManager) getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        wifiadmin = new WifiAdmin(Android_receiveActivity.this);
        wifiCheck = new WifiCheck(this);
        checkNetWork();
        /**
         * 数据连接
         */
        try {
            telephonyManagerClass = Class.forName(telephonyManager
                    .getClass().getName());
            Method   getITelephonyMethod = telephonyManagerClass
                    .getDeclaredMethod("getITelephony");
            getITelephonyMethod.setAccessible(true);
            ITelephonyStub = getITelephonyMethod
                    .invoke(telephonyManager);
            ITelephonyClass = Class.forName(ITelephonyStub.getClass()
                    .getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        Toast.makeText(Android_receiveActivity.this,"您将选择有网传输",Toast.LENGTH_LONG).show();
                        final CustomDialog dialog = new CustomDialog("网络类型选择",Android_receiveActivity.this,
                                R.style.dialogstyle, R.layout.custom_dialog_update);
                        dialog.setOnOkClickListener(new CustomDialog.OnCustomClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog) {
                                wifiadmin.closeNetCard();
                                Toast.makeText(Android_receiveActivity.this, "打开数据连接", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Android_receiveActivity.this, downloadActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        });
                        dialog.setOnCancleClickListener(new CustomDialog.OnCustomClickListener() {
                            @Override
                            public void onClick(CustomDialog dialog) {
                                Toast.makeText(Android_receiveActivity.this, ",打开wifi连接", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Android_receiveActivity.this, downloadActivity.class);
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
                    if (wifiCheck.isWifi()) {
                        /**
                         * 扫描当前热点
                         */
                        Toast.makeText(Android_receiveActivity.this, "您将打开wifi扫描热点连接", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Android_receiveActivity.this, "请打开你的wifi或网络连接", Toast.LENGTH_LONG).show();
                    }
                }
//                    Toast.makeText(Android_receiveActivity.this, "网络未准备好 ！", Toast.LENGTH_SHORT).show();
                //此处可以进行重试处理
                break;
            }
        }
    }

}
