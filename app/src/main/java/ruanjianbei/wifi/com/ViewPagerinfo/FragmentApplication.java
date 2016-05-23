package ruanjianbei.wifi.com.ViewPagerinfo;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.Adpater.AppSelectAdapter;
import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.AppFileInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.AppInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.InterfaceAppInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.AppLoader.utils.DeviceUtils;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;
import util.CustomProgressDialog;


/**
 * Created by zhanghang on 2016/5/16.
 */
public class FragmentApplication extends Fragment
        implements
        AppSelectAdapter.OnItemClickListener
{
    private static final String tag = FragmentApplication.class.getSimpleName();
    public static List<String> mSelectedApp = FragmentChoose.getFileChoose();
    public static final int APP_OK = 1;
    private View view = null;
    private List<InterfaceAppInfo> appList = new ArrayList<>();
    private PackageManager pkManager;
    private AppFragmentHandler handler;
    private AppSelectAdapter adapter;
    private RecyclerView recyclerView;
    private static CustomProgressDialog customProgressDialog = null;
    /**
     * 开启进度框
     */
    private void startDialog(){
        if (customProgressDialog==null)
        {
            customProgressDialog = CustomProgressDialog.getappApplication(IndicatorFragmentActivity.getContext());
            customProgressDialog.setCancelable(false);
            customProgressDialog.setMessage("加载中...");
            customProgressDialog.show();
        }
    }
    /**
     * 隱藏進度對話框
     */
    private static void stopDialog(){
        if(customProgressDialog != null){
            customProgressDialog.cancel();
            customProgressDialog = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        startDialog();
        if (view == null)
        {
            Log.d(tag, "FragmentApplication onCreateView function");
            view = inflater.inflate(R.layout.app_view_select, container, false);
            handler = new AppFragmentHandler(FragmentApplication.this);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            adapter = new AppSelectAdapter(getActivity(), appList);
            adapter.setOnItemClickListener(this);
            recyclerView.setAdapter(adapter);
            getAppInfo();
        }
        return view;
    }

    private void getAppInfo()
    {
        new Thread()
        {
            public void run()
            {
                appList.clear();
                appList.addAll(getApp());
                Log.d(tag, "app list size =" + appList.size());
                Message msg = Message.obtain();
                msg.what = APP_OK;
                handler.sendMessage(msg);
            }
        }.start();
    }

    private List<InterfaceAppInfo> getApp()
    {
        pkManager = App.getInstance().getPackageManager();
        List<ApplicationInfo> listApp = pkManager
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listApp, new ApplicationInfo.DisplayNameComparator(pkManager));
        List<InterfaceAppInfo> appInfo = new ArrayList<>();
        appInfo.clear();
        for (ApplicationInfo app : listApp)
        { // get the third APP
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0)
            {
                AppInfo info = getAppInfo(app);
                if (info == null)
                    continue;
                else if (!appInfo.contains(info))
                    appInfo.add(info);
            }
        }
        return appInfo;
    }

    private AppInfo getAppInfo(ApplicationInfo app)
    {
        AppInfo appInfo = new AppInfo();
        //此地方replace千万不能修改，不是空格，不知道什么东西！！！
        //String label = ((String)app.loadLabel(pkManager)).replace(" ","") + ".apk";
        String label = DeviceUtils.removeSpecial((String) app.loadLabel(pkManager))
                + ".apk";
        appInfo.appLabel = label;
        appInfo.appIcon = app.loadIcon(pkManager);
        appInfo.pkgName = app.packageName;

        String filepath;
        try
        {
            filepath = App.getInstance().getPackageManager()
                    .getApplicationInfo(app.packageName, 0).sourceDir;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        if (filepath == null)
        {
            return null;
        }
        appInfo.appFilePath = filepath;
        File file = new File(filepath);
        long fileSize = file.length();
        if (fileSize <= 0)
            return null;
        String size = DeviceUtils.convertByte(fileSize);
        appInfo.appSize = size;

        return appInfo;
    }
    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        AppInfo info = ((AppInfo) adapter.getItem(position));
        AppFileInfo fileInfo = new AppFileInfo();
        fileInfo.name = info.getFileName();
        fileInfo.type = 0;
        fileInfo.size = new File(info.getFilePath()).length();
        fileInfo.path = info.getFilePath();

        if (FragmentChoose.selectedList.contains(fileInfo))
        {
            FragmentChoose.selectedList.remove(fileInfo);
            mSelectedApp.remove(fileInfo.path);
        }
        else
        {
            FragmentChoose.selectedList.add(fileInfo);
            mSelectedApp.add(fileInfo.path);
            Toast.makeText(getContext(),fileInfo.path,Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
    private static class AppFragmentHandler extends Handler
    {
        private WeakReference<FragmentApplication> weakReference;

        public AppFragmentHandler(FragmentApplication fragment)
        {
            weakReference = new WeakReference<>(fragment);
        }
        @Override
        public void handleMessage(Message msg)
        {
            FragmentApplication fragment = weakReference.get();
            if (fragment == null)
                return;
            if (fragment.getActivity() == null)
                return;

            if (fragment.getActivity().isFinishing())
                return;

            switch (msg.what)
            {
                case APP_OK :
                    stopDialog();
                    fragment.adapter.notifyDataSetChanged();
                    break;
            }

        }
    }

}
