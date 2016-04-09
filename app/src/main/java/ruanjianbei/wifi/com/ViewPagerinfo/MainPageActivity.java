package ruanjianbei.wifi.com.ViewPagerinfo;



import android.os.Bundle;

import java.util.List;

import ruanjianbei.wifi.com.shanchuang.R;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;


public class MainPageActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;
    public static final int FRAGMENT_FIVE = 4;
    public static final int FRAGMENT_SIX = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return FRAGMENT_TWO;
    }

}
