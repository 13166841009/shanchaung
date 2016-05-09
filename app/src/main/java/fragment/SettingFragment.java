package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ruanjianbei.wifi.com.Phone_P_Wifi.WifiShareActivity;

import java.io.ByteArrayInputStream;

import ruanjianbei.wifi.com.my_setting.my_file;
import ruanjianbei.wifi.com.my_setting.my_information;
import ruanjianbei.wifi.com.my_setting.my_music;
import ruanjianbei.wifi.com.my_setting.my_photo;
import ruanjianbei.wifi.com.my_setting.tran_history.tran_history;
import ruanjianbei.wifi.com.my_setting.wait_kaifa;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class SettingFragment extends Activity {
	private static final String load = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/shangchuan/data/image/face.png";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ImageView iv1;
	private ruanjianbei.wifi.com.my_setting.util.DBServiceOperate db;
	private TextView tv_tran;
	//private View mAboutme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mine);
		mContext=this;
		db = new ruanjianbei.wifi.com.my_setting.util.DBServiceOperate(mContext);
		findView();
		init();
		//aboutUs();
	}


	private void findView(){
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
		iv1 = (ImageView) findViewById(R.id.pic);
		iv1.setDrawingCacheEnabled(true);
		tv_tran = (TextView) findViewById(R.id.tv_tran);
	}

	private void init(){
		get_your_infor();
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
	}
	public void get_your_infor(){
		//初始化个人信息
		Bitmap image = ((BitmapDrawable)iv1.getDrawable()).getBitmap();
		if(!db.selectInformation().moveToFirst()) {
			db.saveInformation(image, "熊孩子", "张行", "未知", "110", "110@120.com", "火星");
			db.selectInformation().close();
		}
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
			iv1.setImageDrawable(drawable);//把图片设置到ImageView对象中
		}
		//查看文件传输记录
		ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate db = new
				ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate(mContext);
		Cursor cursor1 = db.selectInformation();
		if(cursor1 != null&&cursor1.getCount()!=0){
			tv_tran.setText("您有"+cursor1.getCount()+"条传输记录，点击查看");
		}else{
			tv_tran.setText("您没有传输记录");
		}
		cursor.close();
	}
	public void aboutMe(View view){
		Intent intentwifi = new Intent(mContext, WifiShareActivity.class);
		startActivity(intentwifi);
//		Intent intent = new Intent(mContext,aboutUs.class);
//		startActivity(intent);
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
