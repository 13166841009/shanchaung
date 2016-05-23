package ruanjianbei.wifi.com.ViewPagerinfo;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import fragment.SettingFragment;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;
import ruanjianbei.wifi.com.shanchuang.R;


public class MainPageActivity extends IndicatorFragmentActivity {
    private long exitTime = 0;
//    private ImageView userimageview;
    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;
    public static final int FRAGMENT_FIVE = 4;
    public static final int FRAGMENT_SIX = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        userimageview = (ImageView) findViewById(R.id.user_imageview);
//        userimageview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingFragment.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, getString(R.string.fragment_one),
                FragmentApplication.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, getString(R.string.fragment_two),
                FragmentPhoto.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_three),
                FragmentMusic.class));
        tabs.add(new TabInfo(FRAGMENT_FOUR, getString(R.string.fragment_four),
                FragmentVideo.class));
        tabs.add(new TabInfo(FRAGMENT_FIVE, getString(R.string.fragment_five),
                FragmentWord.class));
        tabs.add(new TabInfo(FRAGMENT_SIX, getString(R.string.fragment_six),
                FragmentOthers.class));
        return FRAGMENT_ONE;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finishAffinity();
        }
    }
}
