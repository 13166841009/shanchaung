package activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import ruanjianbei.wifi.com.my_setting.util.DBServiceOperate;
import ruanjianbei.wifi.com.shanchuang.R;
import util.SpUtil;
import viewpager.IntroductionPage;

public class WelcomeActivity extends Activity {
	/**
	 * app目录文件结构的创建
	 */
	protected static final String TAG = "WelcomeActivity";
	private Context mContext;
	private ImageView mImageView;
	private SharedPreferences sp;
	private String name;

	/**
	 *主文件目录(+下载)
	 */
	private File maindir;
    private File downdir;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mContext = this;
		initfile();
		findView();
		init();
//		controlUserLogin();//略掉登录界面
	}

//	private void controlUserLogin(){
//		DBServiceOperate db_user = new DBServiceOperate(mContext);
//		Cursor cursor = db_user.selectInformation();
//		if(cursor.getCount()!=0) {
//			if (cursor.moveToFirst()) {//just need to query one time
//				name = cursor.getString(cursor.getColumnIndex("Name"));
//			}
////			JPushInterface.setAlias(mContext,name,null);
//			Intent intent = new Intent(mContext,IntroductionPage.class);
//			startActivity(intent);
//			finish();
//		}
//	}
	private void initfile() {
		maindir = new File(Environment.getExternalStorageDirectory().getPath()+File.separator
				+"shangchuan");
		if(!maindir.exists()){
			maindir.mkdir();
		}
		downdir = new File(Environment.getExternalStorageDirectory().getPath()+File.separator
				+"shangchuan"+File.separator+"Download");
		if(!downdir.exists()) {
			downdir.mkdirs();
		}
	}

	private void findView() {
		mImageView = (ImageView) findViewById(R.id.iv_welcome);
	}

	@SuppressWarnings("static-access")
	private void init() {
		mImageView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//GetDocument.GetDocument(getApplicationContext());
				sp = SpUtil.getInstance().getSharePerference(mContext);
				boolean isFirst = SpUtil.getInstance().isFirst(sp);
				if (!isFirst) {
					SpUtil.getInstance().setBooleanSharedPerference(sp,
							"isFirst", true);
					Intent intent = new Intent(mContext, IntroductionPage.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		},1000);
		
	}
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(mContext);
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(mContext);
	}
}
