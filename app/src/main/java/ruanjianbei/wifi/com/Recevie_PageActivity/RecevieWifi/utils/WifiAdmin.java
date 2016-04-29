package ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell-pc on 2016/4/28.
 */
public class WifiAdmin {
    /**
     * wifi管理类
     */
    private Context context;
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private ScanResult scanResult;
    private List<ScanResult> listResult = new ArrayList<ScanResult>();
    public WifiAdmin(Context context){
        this.context = context;
        //获取系统wifimanager的方法
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //获取当前wifi的连接信息
        wifiInfo = wifiManager.getConnectionInfo();
    }

    //判断是否打开wifi网卡
    public boolean isNetCardFrindly(){
        return wifiManager.isWifiEnabled();
    }

    //打开当前WIFI网卡
    public void openNetCard(){
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }

    //关闭系统当前wifi网卡
    public void closeNetCard(){
        if(wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }
    }
}
