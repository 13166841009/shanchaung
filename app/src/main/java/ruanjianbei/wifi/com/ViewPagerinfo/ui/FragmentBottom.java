package ruanjianbei.wifi.com.ViewPagerinfo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import ruanjianbei.wifi.com.ViewPagerinfo.PopWindowutil.MoreWindow;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by dell-pc on 2016/4/12.
 */
public class FragmentBottom extends Fragment {
    //获取Imagepop对象
    private ImageView BottomImageView;
    /**
     * pager页面底部图标
     */
    //点击图标进行操作
    private MoreWindow mMoreWindow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment_bottom,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomImageView = (ImageView) getActivity().findViewById(R.id.pagebottom);
        BottomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreWindow(v);
            }
        });
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //Toast.makeText(getContext(),"123",Toast.LENGTH_SHORT).show();
//        BottomImageView = (ImageView) VieW.findViewById(R.id.pagebottom);
//        BottomImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMoreWindow(v);
//            }
//        });
//    }

        /**
         * 显示选项进行操作popwindow
         */
    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(getActivity());
            mMoreWindow.init();
        }

        mMoreWindow.showMoreWindow(view, 100);
    }

}
