package ruanjianbei.wifi.com.ScanActivity;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import ruanjianbei.wifi.com.Iospost.IosPost_Activity;
import ruanjianbei.wifi.com.ScanActivity.camera.CameraManager;
import ruanjianbei.wifi.com.ScanActivity.camera.Utils;
import ruanjianbei.wifi.com.ScanActivity.decoding.CaptureActivityHandler;
import ruanjianbei.wifi.com.ScanActivity.decoding.InactivityTimer;
import ruanjianbei.wifi.com.ScanActivity.view.ViewfinderView;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * 扫描Activity
 *
 * @author MrRen
 */
public class ScanningActivity extends Activity implements Callback,
		OnClickListener {
	private String type = null ;//处理类型
	private CaptureActivityHandler handler;// 消息中心
	private ViewfinderView viewfinderView;// 绘制扫描区域
	private boolean hasSurface;// 控制调用相机属性
	private Vector<BarcodeFormat> decodeFormats;// 存储二维格式的数组
	private String characterSet;// 字符集
	private InactivityTimer inactivityTimer;// 相机扫描刷新timer
	private MediaPlayer mediaPlayer;// 播放器
	private boolean playBeep;// 声音布尔
	private static final float BEEP_VOLUME = 0.10f;// 声音大小
	private boolean vibrate;// 振动布尔

	// 闪光灯
	private TextView button_open_lamp;
	private boolean isTorchOn = true;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);
		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		CameraManager.init(this);

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

		// 返回
		Button mButtonBack = (Button) findViewById(R.id.button_back);
		mButtonBack.setOnClickListener(this);

		button_open_lamp = (TextView) findViewById(R.id.button_open_lamp);
		button_open_lamp.setOnClickListener(this);

		// 相册选择
		TextView select_photos_btn = (TextView) findViewById(R.id.select_photos_btn);
		select_photos_btn.setOnClickListener(this);

		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 初始化相机画布
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;
		// 声音
		playBeep = true;
		// 初始化音频管理器
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		// 振动
		vibrate = true;

	}

	@Override
	protected void onPause() {

		// 停止相机 关闭闪光灯
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {

		// 停止相机扫描刷新timer
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	/**
	 * 处理扫描结果
	 *
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(ScanningActivity.this, "扫描失败!", Toast.LENGTH_SHORT)
					.show();
		} else {
			// 根据类型开启不同的界面
			String recode = Utils.recode(result.toString());
			if(type.equals("1")){
				Intent intent = new Intent();
				intent.putExtra("recode",recode);
				intent.setClass(ScanningActivity.this, IosPost_Activity.class);
				startActivity(intent);
				ScanningActivity.this.finish();
			}else if(type.equals("0")){
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(recode));
				startActivity(intent);
				ScanningActivity.this.finish();
			}
		}
	}

	/**
	 * 相册选择图片
	 */
	private void selectPhoto() {

		Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
		if (Build.VERSION.SDK_INT < 19) {
			innerIntent.setAction(Intent.ACTION_GET_CONTENT);
		} else {
			innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		}

		innerIntent.setType("image/*");

		Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");

		ScanningActivity.this.startActivityForResult(wrapperIntent,
				REQUEST_CODE);
	}

	/**
	 * 初始化相机
	 */
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	/**
	 * 声音设置
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	/**
	 * 结束后的声音
	 */
	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	private static final int REQUEST_CODE = 234;// 相册选择code
	private String photo_path;// 选择图片的路径

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			switch (requestCode) {

				case REQUEST_CODE:

					String[] proj = { MediaStore.Images.Media.DATA };
					// 获取选中图片的路径
					Cursor cursor = getContentResolver().query(data.getData(),
							proj, null, null, null);

					if (cursor.moveToFirst()) {

						int column_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						photo_path = cursor.getString(column_index);
						if (photo_path == null) {
							photo_path = Utils.getPath(getApplicationContext(),
									data.getData());
						}

					}

					cursor.close();

					releaseImgThread();

					break;

			}

		}

	}

	/**
	 * 解析图片Thread
	 */
	private void releaseImgThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				Result result = Utils.scanningImage(photo_path);
				if (result == null) {
					msgHandler.sendEmptyMessage(SHOW_TOAST_MSG);

				}
			}
		}).start();
	}

	private static final int SHOW_TOAST_MSG = 0;
	Handler msgHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case SHOW_TOAST_MSG:
					Toast.makeText(getApplicationContext(), "未发现二维码图片", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
			}
		};
	};

	/*
	 * onClick
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_open_lamp:// 闪光灯开关
				if (isTorchOn) {
					isTorchOn = false;
					button_open_lamp.setText("关灯");
					CameraManager.start();
				} else {
					isTorchOn = true;
					button_open_lamp.setText("开灯");
					CameraManager.stop();
				}
				break;
			case R.id.select_photos_btn: {// 选择相册

				selectPhoto();
			}
			break;
			case R.id.button_back: {// 返回
				ScanningActivity.this.finish();
			}
			break;

			default:
				break;
		}

	}
}