package ruanjianbei.wifi.com.ViewPagerinfo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by dell-pc on 2016/4/12.
 */
public class FragmentBottom extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment_bottom,container,false);
        return view;
    }
}
