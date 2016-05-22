package util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;

/**
 *
 *
 */
public class CustomProgressDialog extends Dialog {

	private Context context;
	private static CustomProgressDialog cpd;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context,theme);
	}

	/**
	 * 登陆
	 * @param context
	 * @return
	 */
	public static CustomProgressDialog getInstance(Context context){
		if(cpd == null){
			cpd = new CustomProgressDialog(context, R.style.CustomProgressDialog);
			//设置view
			cpd.setContentView(R.layout.act_custom_progressdialog);
			//设置对话框居中显示
			cpd.getWindow().getAttributes().gravity = Gravity.CENTER;
		}
		return cpd;
	}

	/**
	 * 注册
	 * @param
	 */
	public static CustomProgressDialog getappApplication(Context context){
		if(cpd == null){
			cpd = new CustomProgressDialog(context, R.style.CustomProgressDialog);
			//设置view
			cpd.setContentView(R.layout.act_app_progressdialog);
			//设置对话框居中显示
			cpd.getWindow().getAttributes().gravity = Gravity.CENTER;
		}
		return cpd;
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(cpd == null){
			return;
		}
		ImageView ivLoad = (ImageView) cpd.findViewById(R.id.ivLoad);
		AnimationDrawable ad = (AnimationDrawable) ivLoad.getBackground();
		ad.start();
	}

	/**
	 * 设置标题
	 * @param title 标题}
	 * @return
	 */
	public CustomProgressDialog setTitle(String title) {
		return cpd;
	}

	/**
	 * 设置内容
	 * @param msg 内容
	 * @return
	 */
	public CustomProgressDialog setMessage(String msg) {
		TextView tvLoadMsg = (TextView) cpd.findViewById(R.id.tvLoadMsg);
		if(tvLoadMsg != null){
			tvLoadMsg.setText(msg);
		}
		return cpd;
	}
}
