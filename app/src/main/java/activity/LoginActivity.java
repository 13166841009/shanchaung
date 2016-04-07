package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import ruanjianbei.wifi.com.animation.MainPage;
import ruanjianbei.wifi.com.shanchuang.R;
import util.CustomProgressDialog;
import view.TextURLView;

public class LoginActivity extends Activity {

	private Context mContext;
	private CustomProgressDialog customProgressDialog = null;
	private RelativeLayout rl_user;
	private Button mLogin;
	private EditText username;
	private EditText userpass;
	private Button register;
	private TextURLView mTextViewURL;
	private String url = "http://zh749931552.6655.la/thinkphp/index.php/Index/User_login?";
	private String name = null;
	private String pass =null;
	private String result = null ;//判断返回的类型
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext=this;
		findView();
		initTvUrl();
		init();
	}

	private void findView(){
		rl_user=(RelativeLayout) findViewById(R.id.rl_user);
		mLogin=(Button) findViewById(R.id.login);
		register=(Button) findViewById(R.id.register);
		mTextViewURL=(TextURLView) findViewById(R.id.tv_forget_password);
		username = (EditText) findViewById(R.id.account);
		userpass = (EditText) findViewById(R.id.password);
	}

	private void init(){
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);
		mLogin.setOnClickListener(loginOnClickListener);
		register.setOnClickListener(registerOnClickListener);
	}

	private void initTvUrl(){
		mTextViewURL.setText(R.string.forget_password);
	}

	private OnClickListener loginOnClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			name = username.getText().toString();
			pass = userpass.getText().toString();
			User_login();
		}
	};

	private OnClickListener registerOnClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(mContext, RegisterPhoneActivity.class);
			startActivity(intent);
		}
	};

	/**
	 * 开启进度框
	 */
	private void startDialog(){
		if(customProgressDialog == null){
			customProgressDialog = CustomProgressDialog.getInstance(this);
			customProgressDialog.setMessage("登陆中...");
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
		}
	}
	public void none_account(View v){
		startActivity(new Intent(mContext, MainPage.class));
	}

	private void User_login() {
		startDialog();
		// TODO 自动生成的方法存根
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://zh749931552.6655.la/thinkphp/index.php/Index/User_login?";
		RequestParams params = new RequestParams();
		params.put("name", name);
		params.put("pass", pass);
		client.post(url, params, new JsonHttpResponseHandler() {
			@Override
			public void onFailure(Throwable error) {
				// TODO 自动生成的方法存根
				super.onFailure(error);
				stopDialog();
				Toast.makeText(LoginActivity.this, "请检查您的网络连接!", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);
				try {
					String resultString = response.getString("returncode");
					if(resultString.equals("1")){
						stopDialog();
						Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(mContext,MainPage.class);
						startActivity(intent);
					}else{
						stopDialog();
						Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		});
	}
}
