package ruanjianbei.wifi.com.ViewPagerinfo;



import android.os.Bundle;

import java.util.List;

import ruanjianbei.wifi.com.shanchuang.R;
import ruanjianbei.wifi.com.ui.IndicatorFragmentActivity;


public class MainActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    public static final int FRAGMENT_THREE = 2;
    public static final int FRAGMENT_FOUR = 3;
    public static final int FRAGMENT_FIVE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, getString(R.string.fragment_one),
                FragmentOne.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, getString(R.string.fragment_two),
                FragmentTwo.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_three),
                FragmentThree.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_four),
                FragmentThree.class));
        tabs.add(new TabInfo(FRAGMENT_THREE, getString(R.string.fragment_five),
                FragmentThree.class));
        return FRAGMENT_TWO;
    }

}
