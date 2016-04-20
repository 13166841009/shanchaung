package ruanjianbei.wifi.com.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import ruanjianbei.wifi.com.ScanActivity.camera.Utils;
import ruanjianbei.wifi.com.shanchuang.R;

public class ScanActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;

	/**
	 * 显示扫描拍的图片
	 */
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {// 扫描二维码跳转
				Intent intent = new Intent();
				intent.setClass(ScanActivity.this, ScanningActivity.class);
				startActivity(intent);
			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {// 生成二维码

			@Override
			public void onClick(View v) {
				qrCodeGenerated();
			}
		});
	}

	/**
	 * 二维码生成
	 */
	private void qrCodeGenerated() {

		String str ="21121212121";

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;
		try {

			mImageView.setImageBitmap(Utils.createQRCode(str, width / 2));
			// Resources res = getResources();
			// Bitmap bmp = BitmapFactory.decodeResource(res,
			// R.drawable.ic_xiner);
			// mImageView.setImageBitmap(Utils.createQRCodeLogo(str, width / 2,
			// bmp));

		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
