package ruanjianbei.wifi.com.Recevie_PageActivity;

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
import android.view.View;
import android.widget.Toast;

import com.guo.duoduo.randomtextview.RandomTextView;

import java.util.List;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.WifiApAmdin.WifiApManager;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiRippleOutLayout;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class Android_receiveActivity extends Activity {
    private WifiRippleOutLayout rippleOutLayout;
    private TitleBarView mtitlrbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_receive);
        initTitle();
    }

    private  void initTitle(){
        mtitlrbar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlrbar.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE,View.VISIBLE);
        mtitlrbar.setBtnLeft(R.mipmap.boss_unipay_icon_back,R.string.back);
        mtitlrbar.setTitleText(R.string.wifiAPcreate);
        rippleOutLayout = (WifiRippleOutLayout) findViewById(R.id.activity_receive_ripple_layout);
        rippleOutLayout.startRippleAnimation();
    }
}
