package ruanjianbei.wifi.com.ViewPagerinfo.PopWindowutil;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import ruanjianbei.wifi.com.Post_PageActivity.PostMain.PostActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * 发送底部弹出框
 */
public class MoreWindow extends PopupWindow implements OnClickListener{

	private String TAG = MoreWindow.class.getSimpleName();
	Activity mContext;
	private int mWidth;
	private int mHeight;
	private int statusBarHeight ;
	private Bitmap mBitmap= null;
	private Bitmap overlay = null;
	
	private Handler mHandler = new Handler();

	public MoreWindow(Activity context) {
		mContext = context;
	}

	public void init() {
		Rect frame = new Rect();
		mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		statusBarHeight = frame.top;
		DisplayMetrics metrics = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mHeight = metrics.heightPixels;
		
		setWidth(mWidth);
		setHeight(mHeight);
	}
	
	private Bitmap blur() {
		if (null != overlay) {
			return overlay;
		}
		long startMs = System.currentTimeMillis();

		View view = mContext.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache(true);
		mBitmap = view.getDrawingCache();
		
		float scaleFactor = 8;//图片缩放比例；
		float radius = 10;//模糊程度
		int width = mBitmap.getWidth();
		int height =  mBitmap.getHeight();

		overlay = Bitmap.createBitmap((int) (width / scaleFactor),(int) (height / scaleFactor),Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(mBitmap, 0, 0, paint);

		overlay = FastBlur.doBlur(overlay, (int) radius, true);
		Log.i(TAG, "blur time is:"+(System.currentTimeMillis() - startMs));
		return overlay;
	}
	
	private Animation showAnimation1(final View view,int fromY ,int toY) {
		AnimationSet set = new AnimationSet(true);
		TranslateAnimation go = new TranslateAnimation(0, 0, fromY, toY);
		go.setDuration(300);
		TranslateAnimation go1 = new TranslateAnimation(0, 0, -10, 2);
		go1.setDuration(100);
		go1.setStartOffset(250);
		set.addAnimation(go1);
		set.addAnimation(go);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationEnd(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {

			}

		});
		return set;
	}
	

	public void showMoreWindow(View anchor,int bottomMargin) {
		WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		final RelativeLayout layout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.popwindowactivity, null);
		setContentView(layout);
		
		ImageView close= (ImageView)layout.findViewById(R.id.center_music_window_close);
		LayoutParams params =new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.bottomMargin = bottomMargin;
		params.addRule(RelativeLayout.BELOW, R.id.more_window_auto);
		params.addRule(RelativeLayout.RIGHT_OF, R.id.more_window_collect);
		params.topMargin = 200;
		params.leftMargin = 60;
		close.setLayoutParams(params);
		
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isShowing()) {
					closeAnimation(layout);
				}
			}

		});

		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		layout.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

//				int height = layout.findViewById(R.id.pop_layout).getTop();
				int height = layout.findViewById(R.id.pop_layout).getHeight();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height/2 || y > 1600) {
						closeAnimation(layout);
					}
				}
				return true;
			}
		});

		showAnimation(layout);
		setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
		setOutsideTouchable(true);
		setFocusable(true);
		showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
	}

	private void showAnimation(ViewGroup layout){
		for(int i=0;i<layout.getChildCount();i++){
			final View child = layout.getChildAt(i);
			if(child.getId() == R.id.center_music_window_close){
				continue;
			}
			child.setOnClickListener(this);
			child.setVisibility(View.INVISIBLE);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 500, 0);
					fadeAnim.setDuration(300);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(150);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
				}
			}, i * 50);
		}
		
	}

	private void closeAnimation(ViewGroup layout){
		for(int i=0;i<layout.getChildCount();i++){
			final View child = layout.getChildAt(i);
			if(child.getId() == R.id.center_music_window_close){
				continue;
			}
			child.setOnClickListener(this);
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 500);
					fadeAnim.setDuration(200);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(100);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
					fadeAnim.addListener(new AnimatorListener() {
						
						@Override
						public void onAnimationStart(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationEnd(Animator animation) {
							child.setVisibility(View.INVISIBLE);
							dismiss();
						}
						
						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}, (layout.getChildCount()-i-1) * 30);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_window_collect:
			mContext.startActivity(new Intent(mContext, ReceiveActivity.class));
			Toast.makeText(mContext, "我要接收",  Toast.LENGTH_SHORT).show();
			break;
		case R.id.more_window_auto:
			break;
		case R.id.more_window_external:
			Toast.makeText(mContext, "我要发送",  Toast.LENGTH_SHORT).show();
			Intent intent1 = new Intent(mContext, PostActivity.class);
			mContext.startActivity(intent1);
			break;
		default:
			break;
		}
		dismiss();
	}
	
//	public void destroy() {
//		if (null != overlay) {
//			overlay.recycle();
//			overlay = null;
//			System.gc();
//		}
//		if (null != mBitmap) {
//			mBitmap.recycle();
//			mBitmap = null;
//			System.gc();
//		}
//	}
	
}
