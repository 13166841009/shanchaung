package ruanjianbei.wifi.com.ViewPagerinfo;

import ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.utils.MyToast;
import ruanjianbei.wifi.com.shanchuang.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentApplication extends Fragment implements AdapterView.OnItemClickListener {

	//Handler用来识别的标志
	private static final int SEARCH_APP = 0;
	private static final int DELETE_APP = 1;

	GridView gv;
	ListView lv;
	//加载程序信息的List
	private List<PackageInfo> packageInfo;
	//存放手机所有程序信息的List
	private List<PackageInfo> showPackageInfo;
	//存放用户安装程序信息的List
	private List<PackageInfo> userPackageInfo;

	private View mBaseView;
	private ProgressDialog pd;
	ImageButton ib_change_view;
	ImageButton ib_change_category ;
	private boolean allApplication = true;
	private boolean isListView = false;

	/**
	 * 程序一个Handler，用来接收进程传过来的信息
	 * 然后设置ListView和GridView的适配器
	 */
	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			//接收成功
			if(msg.what == SEARCH_APP)
			{
				showPackageInfo = packageInfo;
				lv.setAdapter(new ListViewAdapter(getActivity(),showPackageInfo));
				gv.setAdapter(new GridViewAdapter(getActivity(),showPackageInfo));
				pd.dismiss();
			}
			else if(msg.what == DELETE_APP){

			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		mBaseView = inflater.inflate(R.layout.fragment_one,null);
		findView();
		initSettings();
		return mBaseView;
	}

	/**
	 * 加载视图
	 */
	private void findView() {
		gv = (GridView)mBaseView.findViewById(R.id.gv_apps);
		lv = (ListView)mBaseView.findViewById(R.id.lv_apps);
		pd = ProgressDialog.show(getActivity(), "温馨提示", "加载中,请稍等...", true, false);
		ib_change_category = (ImageButton)mBaseView.findViewById(R.id.ib_change_category);
		ib_change_view = (ImageButton)mBaseView.findViewById(R.id.ib_change_view);

	}

	/**
	 * 初始化设置
	 */
	private void initSettings() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				packageInfo = mBaseView.getContext().getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_ACTIVITIES);
				userPackageInfo = new ArrayList<PackageInfo>();
				for (int i =0;i<packageInfo.size();i++){
					PackageInfo temp = packageInfo.get(i);
					ApplicationInfo appInfo = temp.applicationInfo;
					boolean flag = false;
					//用户安装的程序
					if((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
						flag = true;
					}
					else if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) ==0){
						flag = true;
					}
					if(flag){
						userPackageInfo.add(temp);
					}
				}
				//进程睡眠3秒
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(SEARCH_APP);
				try {
					Thread.currentThread();
					Thread.sleep(5000);
					//用Handler传送Message
					mHandler.sendEmptyMessage(DELETE_APP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		ib_change_category.setOnClickListener(changeAppsCategory);
		ib_change_view.setOnClickListener(changeListView);
		gv.setOnItemClickListener(this);
		lv.setOnItemClickListener(this);
	}

	//添加GridView ItemClick监听器
	private GridView.OnItemClickListener gvItemClickListener = new GridView.OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
		}
	};
	//ListView ItemClick监听器
	private ListView.OnItemClickListener lvItemClickListener = new ListView.OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
		}
	};

	//点击事件
	private ImageButton.OnClickListener changeListView = new ImageButton.OnClickListener(){
		@Override
		public void onClick(View v) {
			if(isListView){
				MyToast.myToastShow(getActivity(), R.drawable.grids, "表格视图", Toast.LENGTH_SHORT);
				ib_change_view.setImageResource(R.drawable.grids);
				lv.setVisibility(View.GONE);
				gv.setVisibility(View.VISIBLE);
				isListView = false;
			}
			else {
				MyToast.myToastShow(getActivity(), R.drawable.list, "列表视图", Toast.LENGTH_SHORT);
				ib_change_view.setImageResource(R.drawable.list);
				lv.setVisibility(View.VISIBLE);
				gv.setVisibility(View.GONE);
				isListView = true;
			}
		}
	};

	//点击事件
	private ImageButton.OnClickListener changeAppsCategory =  new ImageButton.OnClickListener(){
		@Override
		public void onClick(View v) {

			if(allApplication){
				ib_change_category.setImageResource(R.drawable.user);
				showPackageInfo = userPackageInfo;
				allApplication = false;
				MyToast.myToastShow(getActivity(), R.drawable.user, "用户的APP", Toast.LENGTH_SHORT);
			}
			else{
				MyToast.myToastShow(getActivity(), R.drawable.all, "所有的APP", Toast.LENGTH_SHORT);
				ib_change_category.setImageResource(R.drawable.all);
				showPackageInfo = packageInfo;
				allApplication = true;
			}
			gv.setAdapter(new GridViewAdapter(getActivity(),showPackageInfo));
			lv.setAdapter(new ListViewAdapter(getActivity(),showPackageInfo));
		}
	};

	class GridViewAdapter extends BaseAdapter {

		LayoutInflater inflater;
		List<PackageInfo> pkInfo;
		public GridViewAdapter(Context context,List<PackageInfo> packageInfos){
			inflater = LayoutInflater.from(context);
			this.pkInfo = packageInfos;
		}
		@Override
		public int getCount() {
			return pkInfo.size();
		}

		@Override
		public Object getItem(int arg0) {
			return pkInfo.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.list_gv_item, null);
			TextView tv = (TextView)view.findViewById(R.id.gv_item_appname);
//			TextView tv2= (TextView) view.findViewById(R.id.lv_item_appSize);
			ImageView iv =(ImageView) view.findViewById(R.id.gv_item_icon);
			tv.setText(pkInfo.get(position).applicationInfo.loadLabel(getActivity().getPackageManager()));
			iv.setImageDrawable(pkInfo.get(position).applicationInfo.loadIcon(getActivity().getPackageManager()));
			return view;
		}
	}
	//ListView的适配器类
	class ListViewAdapter extends BaseAdapter{
		LayoutInflater inflater;
		List<PackageInfo> pkInfo;
		public ListViewAdapter(Context context,List<PackageInfo> packageInfos){
			inflater = LayoutInflater.from(context);
			this.pkInfo = packageInfos;
		}
		@Override
		public int getCount() {
			return pkInfo.size();
		}
		@Override
		public Object getItem(int arg0) {
			return pkInfo.get(arg0);
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = inflater.inflate(R.layout.list_item, null);
			TextView tv_appName = (TextView)view.findViewById(R.id.lv_item_appname);
			TextView tv_packageName = (TextView)view.findViewById(R.id.lv_item_packageame);
			ImageView iv =(ImageView) view.findViewById(R.id.lv_icon);

			tv_appName.setText(pkInfo.get(position).applicationInfo.loadLabel(getActivity().getPackageManager()));
			tv_packageName.setText(pkInfo.get(position).packageName);
			iv.setImageDrawable(pkInfo.get(position).applicationInfo.loadIcon(getActivity().getPackageManager()));
			return view;
		}
	}

	/**
	 * ItemClick的处理
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		/**
		 * 选中动作
		 */
	}

	/**
	 *显示程序的详细详细
	 */
	private void showAppDetail(PackageInfo packageInfo) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("程序详细详细");
		StringBuffer messageDetail = new StringBuffer();
		messageDetail.append("程序名称: " + packageInfo.applicationInfo.loadLabel(getActivity().getPackageManager()));
		messageDetail.append("\n包名: " + packageInfo.packageName);
		messageDetail.append("\n版本: " + packageInfo.versionName);
//		messageDetail.append("\n大小: " + packageInfo.size)
		builder.setMessage(messageDetail);
		builder.setIcon(packageInfo.applicationInfo.loadIcon(getActivity().getPackageManager()));
		builder.setPositiveButton("确定", null);
		builder.create().show();
	}

	/**
	 * Activity回传处理
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		packageInfo = getActivity().getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES  | PackageManager.GET_ACTIVITIES);
		userPackageInfo = new ArrayList<PackageInfo>();
		for (int i =0;i<packageInfo.size();i++){
			PackageInfo temp = packageInfo.get(i);
			ApplicationInfo appInfo = temp.applicationInfo;
			boolean flag = false;
			//用户安装的程序
			if((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
				flag = true;
			}
			else if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) ==0){
				flag = true;
			}
			if(flag){
				userPackageInfo.add(temp);
			}
		}
		if(allApplication){
			showPackageInfo = packageInfo;
		}
		else{
			showPackageInfo = userPackageInfo;
		}
		gv.setAdapter(new GridViewAdapter(getActivity(), showPackageInfo));
		lv.setAdapter(new ListViewAdapter(getActivity(), showPackageInfo));
	}
	/**
	 * 回退按钮处理
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
			AlertDialog.Builder alertbBuilder=new AlertDialog.Builder(getActivity());
			alertbBuilder.setTitle("提示").setMessage("确认退出？").setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					getActivity().finish();
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}).create();
			//显示提示框
			alertbBuilder.show();
		}
		return onKeyDown(keyCode, event);
	}
}

