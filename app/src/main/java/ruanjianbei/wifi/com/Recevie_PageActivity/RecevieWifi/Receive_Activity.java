package ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;

import ruanjianbei.wifi.com.Phone_P_Wifi.DataOperation.DataReceiver;
import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaListAdapter;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.WifiApAmdin.WifiApManager;
import ruanjianbei.wifi.com.Recevie_PageActivity.Android_receiveActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.FragmentApplication;
import ruanjianbei.wifi.com.ViewPagerinfo.MainPageActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class Receive_Activity extends Activity {
    private ListView mListView;
    private Button openfile;
    private MediaListAdapter mReceiverMediaListAdapter;
    private DataReceiver mDataReceiver;
    private static final String STORE_DIR = Environment.getExternalStorageDirectory().getPath()+ File.separator
            +"shangchuan"+File.separator+"Download";
    private WifiApManager mwifimanage;
    private TitleBarView mtitlebar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_list);
        inittitle();
        receivermanage();
    }

    private void receivermanage() {
        if (mReceiverMediaListAdapter == null) {
            mReceiverMediaListAdapter = new MediaListAdapter(Receive_Activity.this, null, false);
            if (mDataReceiver == null) {
                mDataReceiver = new DataReceiver(STORE_DIR, mReceiverMediaListAdapter);
            }
            mListView.setAdapter(mReceiverMediaListAdapter);
        }
    }

    private void inittitle() {
        mwifimanage = Android_receiveActivity.getwifiManage();
        mListView = (ListView) findViewById(R.id.receive_media_list_view);
        mtitlebar = (TitleBarView) findViewById(R.id.title_bar);
        mtitlebar.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        mtitlebar.setTitleText(R.string.receivelist);
        mtitlebar.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        mtitlebar.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mwifimanage.stopWifiAp();
                Receive_Activity.this.finish();
                startActivity(new Intent(Receive_Activity.this, ReceiveActivity.class));
            }
        });
        openfile = (Button) findViewById(R.id.opendown_file);
        openfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.fromFile(new File(STORE_DIR+"/".trim())));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mwifimanage.stopWifiAp();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mwifimanage.stopWifiAp();
            Receive_Activity.this.finish();
            startActivity(new Intent(Receive_Activity.this, ReceiveActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }
}
