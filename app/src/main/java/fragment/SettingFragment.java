package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import ruanjianbei.wifi.com.my_setting.aboutUs;
import ruanjianbei.wifi.com.my_setting.my_file;
import ruanjianbei.wifi.com.my_setting.my_information;
import ruanjianbei.wifi.com.my_setting.my_music;
import ruanjianbei.wifi.com.my_setting.my_photo;
import ruanjianbei.wifi.com.my_setting.tran_history;
import ruanjianbei.wifi.com.my_setting.wait_kaifa;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

import static ruanjianbei.wifi.com.my_setting.my_information.getLoacalBitmap;

public class SettingFragment extends Activity {
	private static final String load = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/shangchuan/data/image/face.png";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ImageView iv1;
	//private View mAboutme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mine);
		mContext=this;
		findView();
		init();
		//aboutUs();
	}

	
	private void findView(){
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
		iv1 = (ImageView) findViewById(R.id.pic);
	}

	private void init(){
		//获得本地图片,设置头像
		File file = new File(load);
		if (file.exists())
		{
			Bitmap bitmap = getLoacalBitmap(load);
			iv1.setImageBitmap(bitmap);
		}
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
	}
	public void aboutMe(View view){
		Intent intent = new Intent(mContext,aboutUs.class);
		startActivity(intent);
	}
	public void wait_kf(View view){
		Intent intent = new Intent(mContext,wait_kaifa.class);
		startActivity(intent);
	}
	public void my_photo(View view){
		Intent intent = new Intent(mContext,my_photo.class);
		startActivity(intent);
	}
	public void my_file(View view){
		Intent intent = new Intent(mContext,my_file.class);
		startActivity(intent);
	}
	public void my_music(View view){
		Intent intent = new Intent(mContext,my_music.class);
		startActivity(intent);
	}
	public void tran_history(View view){
		Intent intent = new Intent(mContext,tran_history.class);
		startActivity(intent);
	}
	public void my_information(View view){
		Intent intent = new Intent(mContext,my_information.class);
		startActivity(intent);
	}
}
