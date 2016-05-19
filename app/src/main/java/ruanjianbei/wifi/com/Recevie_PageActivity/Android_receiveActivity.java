package ruanjianbei.wifi.com.Recevie_PageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.WifiApAmdin.WifiApManager;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.Receive_Activity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiRippleOutLayout;
import ruanjianbei.wifi.com.animation.MainPage;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class Android_receiveActivity extends Activity implements WifiApManager.WifiStateListener {
    public static final String ACTION_UPDATE_RECEIVER = "action_update_receiver";
    public static final String EXTRA_DATA = "extra_data";
    private TextView activity_receive_scan_name;
    private static WifiApManager mwifimanage;
    private Boolean mwifiEnable;
    private WifiRippleOutLayout rippleOutLayout;
    private TitleBarView mtitlrbar;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_UPDATE_RECEIVER.equals(action)) {
                Toast.makeText(Android_receiveActivity.this
                        , intent.getStringExtra(EXTRA_DATA), Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_receive);
        initTitle();
        initwifiapmange();
    }

    public static WifiApManager getwifiManage(){
        return mwifimanage;
    }
    private void initwifiapmange() {
        mwifimanage = new WifiApManager(this,this);
        mwifiEnable = mwifimanage.isWifiApEnabled();
        if(!mwifiEnable) {
            mwifimanage.startWifiAp();
        }else{
            Toast.makeText(Android_receiveActivity.this, "热点已经创建", Toast.LENGTH_SHORT).show();
        }
    }

    private  void initTitle(){
        activity_receive_scan_name = (TextView) findViewById(R.id.activity_receive_scan_name);
        mtitlrbar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlrbar.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.VISIBLE);
        mtitlrbar.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        mtitlrbar.setTitleText(R.string.wifiAPcreate);
        mtitlrbar.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Android_receiveActivity.this.finish();
                mwifimanage.stopWifiAp();
            }
        });
        rippleOutLayout = (WifiRippleOutLayout) findViewById(R.id.activity_receive_ripple_layout);
        rippleOutLayout.startRippleAnimation();
    }

    @Override
    public void onScanFinished(List<ScanResult> scanResults) {

    }

    @Override
    public void onSupplicantStateChanged(SupplicantState state, int supplicantError) {

    }

    @Override
    public void onSupplicantConnectionChanged(boolean connected) {

    }

    @Override
    public void onWifiStateChanged(int wifiState, int prevWifiState) {

    }

    @Override
    public void onWifiApStateChanged(int wifiApState) {
        WifiConfiguration wifiConfiguration = mwifimanage.getWifiApConfiguration();
        if(wifiApState==WifiApManager.WIFI_AP_STATE_ENABLED){
            if (wifiConfiguration!=null){
                activity_receive_scan_name.setText(wifiConfiguration.SSID);
                Toast.makeText(Android_receiveActivity.this, "热点"+wifiConfiguration.SSID+"创建成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Android_receiveActivity.this, Receive_Activity.class));
            }
        }
    }

    @Override
    public void onNetworkIdsChanged() {

    }

    @Override
    public void onRSSIChanged(int rssi) {

    }

    @Override
    public void onPickWifiNetwork() {

    }

    @Override
    public void onConnectionPreparing(String ssid) {

    }

    @Override
    public void onConnectionPrepared(boolean success, String ssid) {

    }

    @Override
    public void onConnectNetworkSucceeded(NetworkInfo networkInfo, WifiInfo wifiInfo) {

    }

    @Override
    public void onConnectNetworkFailed(NetworkInfo networkInfo) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //此时设置后退关闭wifiap
//        mwifimanage.stopWifiAp();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Android_receiveActivity.this.finish();
            mwifimanage.stopWifiAp();
        }
        return super.onKeyDown(keyCode, event);
    }
}
