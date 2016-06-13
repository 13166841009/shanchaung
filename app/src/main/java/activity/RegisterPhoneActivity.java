package activity;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TextURLView;
import view.TitleBarView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterPhoneActivity extends Activity {

	private Context mContext;
	private CheckBox privacycheck;
	private String phoneString;
	private Handler handler;
	private String inputveifyCode;
	private String APPKEY = "12d40bbcbcc24";
	private String APPSECERT = "380dd977a417d6884cdf11e28f5402e3";
	private TextView verifyCode;
	private TitleBarView mTitleBarView;
	private TextURLView mTextViewURL;
	private Button next;
	private EditText phone_edit;
	private EditText inputveify;
	private MyCount myCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_phone);
		mContext=this;
		SMSSDK.initSDK(RegisterPhoneActivity.this,APPKEY,APPSECERT);
		initfirst();
		findView();
		initTitleView();
		initTvUrl();
		init();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				if (result == SMSSDK.RESULT_COMPLETE) {
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
							Intent intent=new Intent(mContext,RegisterInfoActivity.class);
							Bundle bundle = new Bundle();
							bundle.putCharSequence("phone",phoneString);
							intent.putExtras(bundle);
							startActivity(intent);
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Toast.makeText(RegisterPhoneActivity.this, "验证码已发送,请稍后", Toast.LENGTH_SHORT).show();
					}
				} else {
					((Throwable) data).printStackTrace();
					Toast.makeText(RegisterPhoneActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	private void initfirst(){
		EventHandler eh = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
	}
	private void findView(){
		privacycheck  = (CheckBox) findViewById(R.id.checkbox_privacy);
		verifyCode = (TextView) findViewById(R.id.add_verify);
		mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
		mTextViewURL=(TextURLView) findViewById(R.id.tv_url);
		next=(Button) findViewById(R.id.btn_next);
		phone_edit = (EditText) findViewById(R.id.et_phoneNumber);
		inputveify = (EditText) findViewById(R.id.input_verify);
	}

	private void init(){
		privacycheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (privacycheck.isChecked()){
					Toast.makeText(RegisterPhoneActivity.this,"您已阅读条款",Toast.LENGTH_SHORT).show();
				}
			}
		});
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//对用户的验证码进行判断
				registerUser("86", phoneString);
			}
		});

		verifyCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				phoneString = phone_edit.getText().toString().trim();
				//获取验证码服务
				SMSSDK.getVerificationCode("86",phoneString);
				myCount = new MyCount(30000, 1000);
				myCount.start();
			}
		});
	}

	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
		mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setTitleText(R.string.title_phoneNumber);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	/*定义一个倒计时的内部类*/
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}
		@Override
		public void onFinish() {
			verifyCode.setEnabled(true);
			verifyCode.setText("获取验证码");
		}
		@Override
		public void onTick(long millisUntilFinished) {
			verifyCode.setText(millisUntilFinished / 1000 + "秒...");
			verifyCode.setEnabled(false);
		}
	}

	private void initTvUrl(){
		mTextViewURL.setText(R.string.tv_xieyi_url);
		mTextViewURL.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(RegisterPhoneActivity.this,"待添加条款",Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 提交用户的信息
	 * @param country
	 * @param phone
	 */
	public void registerUser(String country, String phone) {
		inputveifyCode = inputveify.getText().toString().trim();
		SMSSDK.submitVerificationCode(country, phone, inputveifyCode);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}
}
