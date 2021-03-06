/*
 * @author http://blog.csdn.net/singwhatiwanna
 */
package ruanjianbei.wifi.com.ViewPagerinfo.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import activity.LoginActivity;
import activity.WiFiActivity;
import fragment.UserInfoActivity;
import ruanjianbei.wifi.com.Bluetooth_printer.BluetoothActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.OnSelectItemClickListener;
import ruanjianbei.wifi.com.my_setting.HappyTime;
import ruanjianbei.wifi.com.my_setting.aboutUs;
import ruanjianbei.wifi.com.shanchuang.R;

@SuppressWarnings("static-access")
public abstract class IndicatorFragmentActivity extends FragmentActivity implements OnPageChangeListener,OnSelectItemClickListener{
    private static final String TAG = "DxFragmentActivity";
    private static List<String> userchooosesize = FragmentChoose.getFileChoose();
    public static final String EXTRA_TAB = "tab";
    private ruanjianbei.wifi.com.my_setting.util.DBServiceOperate db;
    //初始化fragment下端
    private FragmentBottom fragmentBottom = new FragmentBottom();
    //用户图像加载
    private static final String load = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/shangchuan/data/image/face.png";
    private ImageView userimage;
    private TextView posttimes;
    private static Toolbar toolbar;

    //private MainPageActivity1 mainPageActivity = new MainPageActivity1();
    protected int mCurrentTab = 0;
    protected int mLastTab = -1;

    //存放选项卡信息的列表
    protected ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

    private static Context mcontext;
    //viewpager adapter
    protected MyAdapter myAdapter = null;

    //viewpager
    protected ViewPager mPager;

    //选项卡控件
    protected TitleIndicator mIndicator;


    public TitleIndicator getIndicator() {
        return mIndicator;
    }

    public static Context getContext(){
        return mcontext;
    }
    private void initViewpager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmentBottom,fragmentBottom).commit();
    }
    public class MyAdapter extends FragmentPagerAdapter {
        ArrayList<TabInfo> tabs = null;
        Context context = null;
        public MyAdapter(Context context, FragmentManager fm, ArrayList<TabInfo> tabs) {
            super(fm);
            this.tabs = tabs;
            this.context = context;
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment fragment = null;
            if (tabs != null && pos < tabs.size()) {
                TabInfo tab = tabs.get(pos);
                if (tab == null)
                    return null;
                fragment = tab.createFragment();
            }
            return fragment;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            if (tabs != null && tabs.size() > 0)
                return tabs.size();
            return 0;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabInfo tab = tabs.get(position);
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            tab.fragment = fragment;
            return fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getMainViewResId());
        toolbar = (Toolbar) findViewById(R.id.activity_file_toolbar);
        toolbar.setTitle("已选文件");
        toolbar.setTitleTextColor(R.color.whites);
        mcontext = this;
        db = new ruanjianbei.wifi.com.my_setting.util.DBServiceOperate(getApplicationContext());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        userimage = (ImageView) view.findViewById(R.id.user_imageview);
        posttimes = (TextView) view.findViewById(R.id.posttimes);
        userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserInfoActivity.class));
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_pcpost) {
                    Intent intent = new Intent(getApplicationContext(), WiFiActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_print) {
                    Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_happy) {
                    Intent intent = new Intent(getApplicationContext(), HappyTime.class);
                    startActivity(intent);
                } else if (id == R.id.nav_aboutus) {
                    startActivity(new Intent(getApplicationContext(), aboutUs.class));
                } else if (id == R.id.nav_share) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    IndicatorFragmentActivity.this.finish();
                } else if (id == R.id.nav_setting) {

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
                //return false;
            }
        });
        initViews();
        settingImage();
        settingposttimes();
        //初始化pager界面的下面部位
        initViewpager();
        //设置viewpager内部页面之间的间距
        mPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin_width));
        //设置viewpager内部页面间距的drawable
        mPager.setPageMarginDrawable(R.color.page_viewer_margin_color);
    }

    private void settingposttimes() {
        //查看文件传输记录
        ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate db = new
                ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate(getApplicationContext());
        Cursor cursor1 = db.selectInformation();
        if(cursor1 != null&&cursor1.getCount()!=0){
            posttimes.setText("传输次数" + cursor1.getCount());
        }
        cursor1.close();
    }

    private void settingImage() {
        //更新头像
        Cursor cursor =  db.selectInformation();
        //取出头像
        byte[] photo = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {//just need to query one time
                photo = cursor.getBlob(cursor.getColumnIndex("Photo"));//取出图片
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        ByteArrayInputStream bais = null;
        if (photo != null) {
            bais = new ByteArrayInputStream(photo);
            Drawable drawable = Drawable.createFromStream(bais, "Photo");
            userimage.setImageDrawable(drawable);//把图片设置到ImageView对象中
        }
        db.Close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onDestroy() {
        mTabs.clear();
        mTabs = null;
        myAdapter.notifyDataSetChanged();
        myAdapter = null;
        mPager.setAdapter(null);
        mPager = null;
        mIndicator = null;
        super.onDestroy();
    }

    private final void initViews() {
        // 这里初始化界面
        mCurrentTab = supplyTabs(mTabs);
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentTab = intent.getIntExtra(EXTRA_TAB, mCurrentTab);
        }
        Log.d(TAG, "mTabs.size() == " + mTabs.size() + ", cur: " + mCurrentTab);
        myAdapter = new MyAdapter(this, getSupportFragmentManager(), mTabs);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(myAdapter);
        mPager.setOnPageChangeListener(this);
        mPager.setOffscreenPageLimit(mTabs.size());

        mIndicator = (TitleIndicator) findViewById(R.id.pagerindicator);
        mIndicator.init(mCurrentTab, mTabs, mPager);
        mPager.setCurrentItem(mCurrentTab);
        mLastTab = mCurrentTab;
    }

    @Override
    public void onItemClicked( ) {
        updateBottom();
    }

    public static void updateBottom() {
        toolbar.setTitle("已选文件:"+userchooosesize.size()+"个");
    }


    /**
     * 添加一个选项卡
     * @param tab
     */
    public void addTabInfo(TabInfo tab) {
        mTabs.add(tab);
        myAdapter.notifyDataSetChanged();
    }

    /**
     * 从列表添加选项卡
     * @param tabs
     */
    public void addTabInfos(ArrayList<TabInfo> tabs) {
        mTabs.addAll(tabs);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.onScrolled((mPager.getWidth() + mPager.getPageMargin()) * position + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.onSwitched(position);
        mCurrentTab = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mLastTab = mCurrentTab;
        }
    }

    protected TabInfo getFragmentById(int tabId) {
        if (mTabs == null) return null;
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            TabInfo tab = mTabs.get(index);
            if (tab.getId() == tabId) {
                return tab;
            }
        }
        return null;
    }
    /**
     * 跳转到任意选项卡
     * @param tabId 选项卡下标
     */
    public void navigate(int tabId) {
        for (int index = 0, count = mTabs.size(); index < count; index++) {
            if (mTabs.get(index).getId() == tabId) {
                mPager.setCurrentItem(index);
            }
        }
    }
    /**
     * 返回layout id
     * @return layout id
     */
    protected int getMainViewResId() {
        return R.layout.activity_main_title;
    }

    /**
     * 在这里提供要显示的选项卡数据
     */
    protected abstract int supplyTabs(List<TabInfo> tabs);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // for fix a known issue in support library
        // https://code.google.com/p/android/issues/detail?id=19917
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    /**
     * 单个选项卡类，每个选项卡包含名字，图标以及提示（可选，默认不显示）
     */
    public static class TabInfo implements Parcelable {

        private int id;
        private int icon;
        private String name = null;
        public boolean hasTips = false;
        public Fragment fragment = null;
        public boolean notifyChange = false;
        @SuppressWarnings("rawtypes")
        public Class fragmentClass = null;

        @SuppressWarnings("rawtypes")
        public TabInfo(int id, String name, Class clazz) {
            this(id, name, 0, clazz);
        }

        @SuppressWarnings("rawtypes")
        public TabInfo(int id, String name, boolean hasTips, Class clazz) {
            this(id, name, 0, clazz);
            this.hasTips = hasTips;
        }

        @SuppressWarnings("rawtypes")
        public TabInfo(int id, String name, int iconid, Class clazz) {
            super();

            this.name = name;
            this.id = id;
            icon = iconid;
            fragmentClass = clazz;
        }

        public TabInfo(Parcel p) {
            this.id = p.readInt();
            this.name = p.readString();
            this.icon = p.readInt();
            this.notifyChange = p.readInt() == 1;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIcon(int iconid) {
            icon = iconid;
        }

        public int getIcon() {
            return icon;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Fragment createFragment() {
            if (fragment == null) {
                Constructor constructor;
                try {
                    constructor = fragmentClass.getConstructor(new Class[0]);
                    fragment = (Fragment) constructor.newInstance(new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return fragment;
        }

        public static final Creator<TabInfo> CREATOR = new Creator<TabInfo>() {
            public TabInfo createFromParcel(Parcel p) {
                return new TabInfo(p);
            }

            public TabInfo[] newArray(int size) {
                return new TabInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel p, int flags) {
            p.writeInt(id);
            p.writeString(name);
            p.writeInt(icon);
            p.writeInt(notifyChange ? 1 : 0);
        }

    }

}
