package fragment;


import ruanjianbei.wifi.com.shanchuang.Mobile_PostAndroid;
import ruanjianbei.wifi.com.shanchuang.R;
import ruanjianbei.wifi.com.shanchuang.UserGame;
import ruanjianbei.wifi.com.shanchuang.Wifi_PostAndroid;
import view.TitleBarView;
import activity.WiFiActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyPostFragment extends Fragment {

	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private RelativeLayout wifi;
	private RelativeLayout phone_post;
	private RelativeLayout user_game;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_dynamic, null);
		findView();
		initTitleView();
		init();
		return mBaseView;
	}

	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		wifi=(RelativeLayout) mBaseView.findViewById(R.id.wifi);
		phone_post = (RelativeLayout) mBaseView.findViewById(R.id.Phone_post);
		user_game = (RelativeLayout) mBaseView.findViewById(R.id.User_game);
	}

	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.dynamic);
	}
	private void init(){
		/**
		 * 电脑传输
		 */
		wifi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, WiFiActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);

			}
		});
		/**
		 * 手机传输
		 */
		phone_post.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activieNetWork =  connectivityManager.getActiveNetworkInfo();
			if(activieNetWork.isConnectedOrConnecting()){
				if (activieNetWork.getType()==ConnectivityManager.TYPE_MOBILE){
					Toast.makeText(mContext,"3G/4G",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(mContext, Mobile_PostAndroid.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				}else if (activieNetWork.getType()==ConnectivityManager.TYPE_WIFI){
					Toast.makeText(mContext,"wifi",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(mContext, Wifi_PostAndroid.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				}else {
					Toast.makeText(mContext,"蓝牙",Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(mContext,"断网了",Toast.LENGTH_SHORT).show();
			}

		}

		});
		/**
		 * 打地鼠
		 */
		user_game.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, UserGame.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
			}
		});
	}

}
