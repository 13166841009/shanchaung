package ruanjianbei.wifi.com.Post_PageActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.DhcpInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ruanjianbei.wifi.com.Phone_P_Wifi.DataOperation.DataSender;
import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaItem;
import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaListAdapter;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.Utils;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.WifiApAmdin.WifiApManager;
import ruanjianbei.wifi.com.Post_PageActivity.PostMain.PostActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

/**
 * 文件传输列表
 */
public class Post_Activity extends Activity {
    //Adpater的列表展示
    private WifiAdmin wifiAdmin;
    private WifiApManager mwifimanage;
    private TitleBarView mtitlebar;
    private MediaListAdapter mSenderMediaListAdapter;
    private DataSender mDataSender;
    private WifiApManager mWifiApmanage;
    private ListView mListView;
    private ArrayList<String> testlist = (ArrayList<String>) FragmentChoose.getFileChoose();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        wifiAdmin = new WifiAdmin(Post_Activity.this);
        mtitlebar = (TitleBarView) findViewById(R.id.title_bar);
        inittitle();
        mListView = (ListView)findViewById(R.id.post_media_list_view);
        mWifiApmanage = Android_postActivity.getwifiApManager();
        if(mSenderMediaListAdapter ==null){
            mSenderMediaListAdapter = new MediaListAdapter(this,queryExtrernalMedias(),true);
            mSenderMediaListAdapter.setSendListener(new MediaListAdapter.OnSendClickListener() {
                @Override
                public void onSendEvent(MediaListAdapter.TransmittableMediaItem item) {
                    DhcpInfo dhcpInfo = mWifiApmanage.getWifiManager().getDhcpInfo();
                    if(mDataSender==null){
                        mDataSender =  new DataSender(Utils.intToIpString(dhcpInfo.gateway),mSenderMediaListAdapter);
                    }
                    mDataSender.send(item.mMediaItem);
                }
            });
            mListView.setAdapter(mSenderMediaListAdapter);
        }
    }

    private void inittitle() {
        mtitlebar.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        mtitlebar.setTitleText(R.string.post_listview);
        mtitlebar.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        mtitlebar.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiAdmin.closeNetCard();
                Post_Activity.this.finish();
                startActivity(new Intent(Post_Activity.this, PostActivity.class));
            }
        });
    }

    private List<MediaItem> queryExtrernalMedias() {
        int i = 0;
        if(testlist!=null){
            List<MediaItem> medias = new ArrayList<MediaItem>();
            for(String s:testlist){
                medias.add(new MediaItem(i));
                i++;
            }
            return medias;
        }
        return null;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            wifiAdmin.closeNetCard();
            Post_Activity.this.finish();
            startActivity(new Intent(Post_Activity.this, PostActivity.class));
        }
        return super.onKeyDown(keyCode, event);
    }
}
