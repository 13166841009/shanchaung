package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ruanjianbei.wifi.com.Phone_P_3G.aboutActivity;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class SettingFragment extends Activity {

	private Context mContext;
	private TitleBarView mTitleBarView;
	//private View mAboutme;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mine);
		mContext=this;
		findView();
		init();
		//aboutMe();
	}

	
	private void findView(){
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
		//mAboutme = findViewById(R.id.about_us);
	}

	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
	}
	public void aboutMe(View view){
		Toast.makeText(SettingFragment.this,"介绍",Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(mContext,aboutActivity.class);
		startActivity(intent);
	}
}
