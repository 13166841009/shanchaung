package activity;


import ruanjianbei.wifi.com.Phone_P_3G.upload.uploadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.WifiPcDirect.WifiPcActivity;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;
import foregin.UiUpdater;

import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.bruce.library.ComboView;

@SuppressLint("NewApi")
public class WiFiActivity extends Activity {
	private TextView ipText;
	private TitleBarView mTitleBarView;

	//protected MyLog myLog = new MyLog(this.getClass().getName());

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0: // We are being told to do a UI update
					// If more than one UI update is queued up, we only need to
					// do one.
					removeMessages(0);
					updateUi();
					break;
				case 1: // We are being told to display an error message
					removeMessages(1);
			}
		}
	};

	private Activity mActivity;

	public WiFiActivity() {}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pc_connect_wifi);

		mActivity = WiFiActivity.this;
		// Set the application-wide context global, if not already set
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		updateUi();
		UiUpdater.registerClient(handler);

		// quickly turn on or off wifi.
		findViewById(R.id.wifi_state_image).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(
								android.provider.Settings.ACTION_WIFI_SETTINGS);
						startActivity(intent);
					}
				});

		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.wifi);
		ComboView pcwifi = (ComboView) findViewById(R.id.pc_wifi_post);
		ComboView.Params params = ComboView.Params.create()

				//Android文件传输
				.cornerRadius(dimen(R.dimen.cb_dimen_25), dimen(R.dimen.cb_dimen_52))// Following three to***** values must be the same can morph to circle
				.width(dimen(R.dimen.cb_dimen_70), dimen(R.dimen.cb_dimen_52))
				.height(dimen(R.dimen.cb_dimen_38), dimen(R.dimen.cb_dimen_52))
				.morphDuration(300)
				.text("PC连接模式", "点击进入")
						//Option -- and values below is default
				.color(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
				.colorPressed(color(R.color.cb_color_blue_dark), color(R.color.cb_color_blue_dark))
				.strokeWidth(dimen(R.dimen.cb_dimen_1), dimen(R.dimen.cb_dimen_1))
				.strokeColor(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
				.circleDuration(3000)
				.rippleDuration(200)
				.padding(dimen(R.dimen.cb_dimen_3))
				.textSize(16)
				.textColor(color(R.color.cb_color_white))
				.comboClickListener(new ComboView.ComboClickListener() {
					@Override
					public void onComboClick() {
						startActivity(new Intent(WiFiActivity.this, WifiPcActivity.class));
					}

					@Override
					public void onNormalClick() {
					}
				});
		pcwifi.settingMorphParams(params);
	}

	public int dimen(@DimenRes int resId) {
		return (int) getResources().getDimension(resId);
	}

	public int color(@ColorRes int resId) {
		return getResources().getColor(resId);
	}
	   /* @Override
	    public boolean onBack() {
	        return false;
	    }*/

	/**
	 * Whenever we regain focus, we should update the button text depending on
	 * the state of the server service.
	 */
	public void onStart() {
		super.onStart();
		UiUpdater.registerClient(handler);
		updateUi();
	}

	public void onResume() {
		super.onResume();
		UiUpdater.registerClient(handler);
		updateUi();
		// Register to receive wifi status broadcasts
		//myLog.l(Log.DEBUG, "Registered for wifi updates");
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		mActivity.registerReceiver(wifiReceiver, filter);
	}

	/*
     * Whenever we lose focus, we must unregister from UI update messages from
     * the FTPServerService, because we may be deallocated.
     */
	public void onPause() {
		super.onPause();
		UiUpdater.unregisterClient(handler);
		// myLog.l(Log.DEBUG, "Unregistered for wifi updates");
		mActivity.unregisterReceiver(wifiReceiver);
	}

	public void onStop() {
		super.onStop();
		UiUpdater.unregisterClient(handler);
	}

	public void onDestroy() {
		super.onDestroy();
		UiUpdater.unregisterClient(handler);
	}

	/**
	 * This will be called by the static UiUpdater whenever the service has
	 * changed state in a way that requires us to update our UI. We can't use
	 * any myLog.l() calls in this function, because that will trigger an
	 * endless loop of UI updates.
	 */
	public void updateUi() {
		WifiManager wifiMgr = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
		int wifiState = wifiMgr.getWifiState();
		WifiInfo info = wifiMgr.getConnectionInfo();
		String wifiId = info != null ? info.getSSID() : null;
		WifiAdmin wifiAdmin = new WifiAdmin(this);
		boolean isWifiReady = wifiAdmin.isNetCardFrindly();
		setText(R.id.wifi_state, isWifiReady ? wifiId : getString(R.string.no_wifi_hint));
		ImageView wifiImg = (ImageView)findViewById(R.id.wifi_state_image);
		wifiImg.setImageResource(isWifiReady ? R.mipmap.wifi_state : R.mipmap.wifi_state_statu);
	}
	private void setText(int id, String text) {
		TextView tv = (TextView) findViewById(id);
		tv.setText(text);
	}
	BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
		public void onReceive(Context ctx, Intent intent) {
			//myLog.l(Log.DEBUG, "Wifi status broadcast received");
			updateUi();
		}
	};
}
