package ruanjianbei.wifi.com.ViewPagerinfo.ui;

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

import ruanjianbei.wifi.com.ViewPagerinfo.PopWindowutil.MoreWindow;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by dell-pc on 2016/4/12.
 */
public class FragmentBottom extends Fragment {
    //获取Imagepop对象
    private Button BottomImageView;
    private ImageView imageView;
    public int getCount_file() {
        return count_file;
    }

    public void setCount_file(int count_file) {
        this.count_file = count_file;
    }

    /**
     * 记录选择文件的个数
     */

    public void changetext(String text){
        counttext.setText(text);
    }
    private int count_file = 0;
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
        imageView = (ImageView) getActivity().findViewById(R.id.bottomimage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.isDrawerOpen(GravityCompat.START);
            }
        });
        BottomImageView = (Button) getActivity().findViewById(R.id.pagebottom);
        BottomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreWindow(v);
            }
        });
    }

//    public Thread thread = new Thread(){
//        @Override
//        public void run() {
//            while (true) {
//                counttext.setText(count_file);
//            }
//        }
//    };
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //Toast.makeText(getContext(),"123",Toast.LENGTH_SHORT).show();
//        BottomImageView = (ImageView) VieW.findViewById(R.id.mainpagebottom);
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

        mMoreWindow.showMoreWindow(view, 200);
    }

}
