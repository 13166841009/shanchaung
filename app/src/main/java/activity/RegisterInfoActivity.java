package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import ruanjianbei.wifi.com.ViewPagerinfo.MainPageActivity;
import ruanjianbei.wifi.com.shanchuang.R;
import util.CustomProgressDialog;
import util.DialogHelp;
import util.MyApplication;
import view.TitleBarView;

public class RegisterInfoActivity extends Activity {
	private Context mContext;
	private CustomProgressDialog customProgressDialog;
	private Button btn_complete;
	private TitleBarView mTitleBarView;
	private EditText username;
	private EditText userpass;
	private String name;
	private String pass;
	private String phone;
	private static String url = MyApplication.URL+"/thinkphp/index.php/Index/User_regedit?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_userinfo);
		mContext=this;
		findView();
		initTitleView();
		init();
	}

	private void findView(){
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		btn_complete=(Button) findViewById(R.id.register_complete);
		userpass = (EditText) findViewById(R.id.password);
		username = (EditText) findViewById(R.id.name);
	}

	private void init(){
		btn_complete.setOnClickListener(completeOnClickListener);
	}
	/**
	 * 开启进度框
	 */
	private void startDialog(){
		if(customProgressDialog == null){
			customProgressDialog = CustomProgressDialog.getappApplication(mContext);
			customProgressDialog.setCancelable(false);
			customProgressDialog.setMessage("注册中...");
		}
		customProgressDialog.show();
	}
	/**
	 * 隱藏進度對話框
	 */
	private void stopDialog(){
		if(customProgressDialog != null){
			customProgressDialog.cancel();
			customProgressDialog = null;
		}else{
			customProgressDialog.cancel();
		}
	}
	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.title_register_info);
		mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterInfoActivity.this,LoginActivity.class));
				RegisterInfoActivity.this.finish();
			}
		});
	}

	private OnClickListener completeOnClickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent inten = getIntent();
			Bundle bundle = inten.getExtras();
			phone = bundle.getString("phone");
			name = username.getText().toString().trim();
			pass = userpass.getText().toString().trim();
			if(name.equals("")||pass.equals("")||phone.equals("")){
				Toast.makeText(RegisterInfoActivity.this, "请输入您的信息",Toast.LENGTH_SHORT).show();
			}else{
				regedit_user();
			}
		}
	};


	private void regedit_user() {
		startDialog();
		// TODO 自动生成的方法存根
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("phone",phone);
		params.put("pass", pass);
		client.post(url, params, new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable error) {
				// TODO 自动生成的方法存根
				super.onFailure(error);
				stopDialog();
				Toast.makeText(RegisterInfoActivity.this, "请检查您的网络连接",Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(JSONObject response) {
				// TODO 自动生成的方法存根
				super.onSuccess(response);
				try {
					String resultString = response.getString("returncode");
					if(resultString.equals("1")){
						stopDialog();
						Toast.makeText(RegisterInfoActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(RegisterInfoActivity.this, MainPageActivity.class));
						RegisterInfoActivity.this.finish();
					}else if(resultString.equals("0")){
						stopDialog();
						Toast.makeText(RegisterInfoActivity.this, "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(RegisterInfoActivity.this, LoginActivity.class));
					}else{
						stopDialog();
						Toast.makeText(RegisterInfoActivity.this, "信息不全", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK){
			startActivity(new Intent(RegisterInfoActivity.this, LoginActivity.class));
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

