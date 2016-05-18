package ruanjianbei.wifi.com.Post_PageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.guo.duoduo.randomtextview.RandomTextView;

import java.util.List;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.WifiApAmdin.WifiApManager;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiRippleOutLayout;
import ruanjianbei.wifi.com.ViewPagerinfo.MainPageActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.PopWindowutil.MoreWindow;
import ruanjianbei.wifi.com.animation.MainPage;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class Android_postActivity extends Activity implements WifiApManager.WifiStateListener{
    public static final String ACTION_UPDATE_RECEIVER = "action_update_receiver";
    public static final String EXTRA_DATA = "extra_data";
    private static WifiApManager mWifiApManager;
    private RandomTextView randomTextView;
    private WifiAdmin wifiAdmin;
    private TitleBarView mtitlrbar;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_UPDATE_RECEIVER.equals(action)) {
                Toast.makeText(Android_postActivity.this
                        ,intent.getStringExtra(EXTRA_DATA),Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recevie_wifi);
        initTitle();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        mWifiApManager = new WifiApManager(this, this);
        wifiAdmin = new WifiAdmin(Android_postActivity.this);
        wifiAdmin.openNetCard();
        mWifiApManager.startScan();
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_RECEIVER));
        randomTextView = (RandomTextView) findViewById(
                R.id.random_textview);
    }

    @Override
    public void onScanFinished(List<ScanResult> scanResults) {

    }

    public static WifiApManager getwifiApManager(){
        return mWifiApManager;
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
        Toast.makeText(Android_postActivity.this,"热点"+ssid+"准备中",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionPrepared(boolean success, String ssid) {
        if(success){
            Toast.makeText(Android_postActivity.this,"热点"+ssid+"连接中...",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Android_postActivity.this,"热点"+ssid+"不能连接！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectNetworkSucceeded(NetworkInfo networkInfo, final WifiInfo wifiInfo) {
        if (!wifiInfo.getSSID().contains(WifiApManager.SSID_PREFIX)) {
            return;
        }
        Toast.makeText(Android_postActivity.this,"连接成功"
                +wifiInfo.getBSSID(),Toast.LENGTH_SHORT).show();
        randomTextView.setOnRippleViewClickListener(
                new RandomTextView.OnRippleViewClickListener() {
                    @Override
                    public void onRippleViewClicked(View view) {
                        Intent intent = new Intent();
                        intent.setClass(Android_postActivity.this, Post_Activity.class);
                        startActivity(intent);
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                randomTextView.addKeyWord(wifiInfo.getSSID());
                randomTextView.show();
            }
        }, 2 * 1000);
    }

    @Override
    public void onConnectNetworkFailed(NetworkInfo networkInfo) {
        //Toast.makeText(Android_postActivity.this,"请您退出重新连接",Toast.LENGTH_SHORT).show();
    }
    /**
     * 关闭wifi扫描
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        wifiAdmin.closeNetCard();
        //mWifiApManager.destroy(Android_postActivity.this);
    }
    private  void initTitle(){
        mtitlrbar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlrbar.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE,View.VISIBLE);
        mtitlrbar.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        mtitlrbar.setTitleText(R.string.wifiscan);
    }
}
