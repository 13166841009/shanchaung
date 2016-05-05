package ruanjianbei.wifi.com.Wifiandroid_transfer;

import android.app.Activity;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import ruanjianbei.wifi.com.Wifiandroid_transfer.utils.WifiApManager;
import ruanjianbei.wifi.com.shanchuang.R;

public class WifiTranAndroid extends Activity implements WifiApManager.WifiStateListener {
    private WifiApManager mWifiApManager;
    private boolean mWifiApEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_tran_android);
        mWifiApManager = new WifiApManager(this,this);
        mWifiApEnable = mWifiApManager.isWifiApEnabled();
        mWifiApEnable = !mWifiApEnable;
        if (mWifiApEnable) {
            Toast.makeText(WifiTranAndroid.this,"wifi热点创建中",Toast.LENGTH_SHORT).show();
            mWifiApManager.startWifiAp();
//            mInfo.setText(mWifiApManager.startWifiAp() ? "wifi热点创建中...." : "wifi热点创建失败");
        } else {
            mWifiApManager.stopWifiAp();
            Toast.makeText(WifiTranAndroid.this,"不能接收数据",Toast.LENGTH_SHORT).show();
//            mInfo.setText("WIFI热点已关闭，不能接收数据");
        }
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
        WifiConfiguration configuration = mWifiApManager.getWifiApConfiguration();
        if (wifiApState == WifiApManager.WIFI_AP_STATE_ENABLED) {
            Log.i("wifi建立", "WIFI_AP_STATE_ENABLED");
            if (configuration != null) {
                Toast.makeText(WifiTranAndroid.this,"已建立WIFI热点",Toast.LENGTH_SHORT).show();
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
}
