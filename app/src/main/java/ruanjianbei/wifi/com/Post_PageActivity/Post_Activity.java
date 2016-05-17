package ruanjianbei.wifi.com.Post_PageActivity;

import android.app.Activity;
import android.net.DhcpInfo;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ruanjianbei.wifi.com.Phone_P_Wifi.DataOperation.DataSender;
import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaItem;
import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaListAdapter;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.Utils;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.WifiApAmdin.WifiApManager;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

public class Post_Activity extends Activity {
    //Adpater的列表展示
    private MediaListAdapter mSenderMediaListAdapter;
    private DataSender mDataSender;
    private WifiApManager mWifiApmanage;
    private ListView mListView;
    /**
     * 添加
     */
    private ArrayList<String> testlist = (ArrayList<String>) FragmentChoose.getFileChoose();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
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
}
