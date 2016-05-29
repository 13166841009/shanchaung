package ruanjianbei.wifi.com.ViewPagerinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.PopWindowutil.MoreWindow;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by dell-pc on 2016/4/12.
 */
public class FragmentBottom extends Fragment {
    //获取Imagepop对象
    private Button BottomImageView;
    private Button receivebutton;
    /**
     * pager页面底部图标
     */
    //点击图标进行操作
    private MoreWindow mMoreWindow;
    //选择文件的数量
    private TextView counttext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment_bottom,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BottomImageView = (Button) getActivity().findViewById(R.id.pagebottom);
        BottomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreWindow(v);
            }
        });
        receivebutton = (Button) getActivity().findViewById(R.id.receive_bottom);
        receivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ReceiveActivity.class));
            }
        });
    }
    /**
     * 显示选项进行操作popwindow
     */
    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(getActivity());
            mMoreWindow.init();
        }

        mMoreWindow.showMoreWindow(view, 200);
    }

}
