package activity;



import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.GetDocument;
import ruanjianbei.wifi.com.shanchuang.R;
import util.SpUtil;
import test.SDManager;
import viewpager.IntroductionPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;

public class WelcomeActivity extends Activity {
	/**
	 * app目录文件结构的创建
	 */
	protected static final String TAG = "WelcomeActivity";
	private Context mContext;
	private ImageView mImageView;
	private SharedPreferences sp;

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
	}

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
					SDManager manager = new SDManager(mContext);
					manager.moveUserIcon();
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
}
