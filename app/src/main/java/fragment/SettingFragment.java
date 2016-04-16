package fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class SettingFragment extends Activity {

	private Context mContext;
	private TitleBarView mTitleBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mine);
		mContext=this;
		findView();
		init();
	}

	
	private void findView(){
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
	}

	private void init(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
	}

}
