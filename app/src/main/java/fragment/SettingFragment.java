package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import ruanjianbei.wifi.com.my_setting.my_information;
import ruanjianbei.wifi.com.my_setting.tran_history.tran_history;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class SettingFragment extends Activity {
	private static final String load = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/shangchuan/data/image/face.png";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ruanjianbei.wifi.com.my_setting.util.DBServiceOperate db;
	private TextView tv_tran;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mine);
		mContext=this;
		db = new ruanjianbei.wifi.com.my_setting.util.DBServiceOperate(mContext);
		findView();
		init();
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
		mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SettingFragment.this.finish();
			}
		});
	}


	private void findView(){
		tv_tran = (TextView) findViewById(R.id.tv_tran);
	}

	private void init(){
		get_your_infor();
	}
	public void get_your_infor(){
		//查看文件传输记录
		ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate db = new
				ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate(mContext);
		Cursor cursor1 = db.selectInformation();
		if(cursor1 != null&&cursor1.getCount()!=0){
			tv_tran.setText("您有"+cursor1.getCount()+"条传输记录，点击查看");
		}else{
			tv_tran.setText("您没有传输记录");
		}
		cursor1.close();
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
