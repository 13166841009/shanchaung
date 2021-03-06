package ruanjianbei.wifi.com.ViewPagerinfo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.bean.ImageFloder;
import ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.imageloder.ListImageDirPopupWindow;
import ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.imageloder.ImageAdapter;
import ruanjianbei.wifi.com.shanchuang.R;


public class FragmentPhoto extends Fragment implements ListImageDirPopupWindow.OnImageDirSelected
{
	private View mBaseView;
	//private FragmentManager fragmentManager;
	/**
	 * 存储文件夹中的图片数量
	 */
	private int mPicsSize;
	/**
	 * 图片数量最多的文件夹
	 */
	private File mImgDir;
	/**
	 * 所有的图片
	 */
	private List<String> mImgs;

	private GridView mGirdView;
	private ImageAdapter mAdapter;
	/**
	 * 临时的辅助类，用于防止同一个文件夹的多次扫描
	 */
	private HashSet<String> mDirPaths = new HashSet<String>();

	/**
	 * 扫描拿到所有的图片文件夹
	 */
	private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();

	private RelativeLayout mBottomLy;

	//private TextView mChooseDir;
	//private TextView mImageCount;
	//int totalCount = 0;

	private int mScreenHeight;
	private int mScreenWidth;

	private ListImageDirPopupWindow mListImageDirPopupWindow;

	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			//mProgressDialog.dismiss();
			// 为View绑定数据
			data2View();
			// 初始化展示文件夹的popupWindw
			initListDirPopupWindw();
		}
	};

	/**
	 * 为View绑定数据
	 */
	private void data2View()
	{
		if (mImgDir == null)
		{
			Toast.makeText(this.getActivity(), "目前目录没有图片哟",
					Toast.LENGTH_SHORT).show();
			return;
		}

		mImgs = Arrays.asList(mImgDir.list());
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new ImageAdapter(this.getActivity(), mImgs,
				R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		//mImageCount.setText(totalCount + "张");
	}

	/**
	 * 初始化展示文件夹的popupWindw
	 */
	private void initListDirPopupWindw()
	{
		mListImageDirPopupWindow = new ListImageDirPopupWindow(
				(int) (mScreenWidth * 1), (int) (mScreenHeight * 1),
				mImageFloders, LayoutInflater.from(this.getActivity())
				.inflate(R.layout.list_dir, null));

		mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				// 设置背景颜色变暗
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1.0f;
				getActivity().getWindow().setAttributes(lp);
			}
		});
		// 设置选择文件夹的回调
		mListImageDirPopupWindow.setOnImageDirSelected(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		//fragmentManager = getActivity().getSupportFragmentManager();
		mBaseView=inflater.inflate(R.layout.fragment_main, null);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		mScreenHeight = outMetrics.heightPixels;
		mScreenWidth = outMetrics.widthPixels;
		initView();
		getImages();
//		initEvent();
		return mBaseView;
	}
	/**
	 * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
	 */
	private void getImages()
	{
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED))
		{
			Toast.makeText(this.getActivity(), "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}
		// 显示进度条
		//mProgressDialog = ProgressDialog.show(this.getActivity(), null, "正在加载...");

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{

				String firstImage = null;

				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver mContentResolver = FragmentPhoto.this.getActivity()
						.getContentResolver();

				// 只查询jpeg和png的图片
				Cursor mCursor = mContentResolver.query(mImageUri, null,
						MediaStore.Images.Media.MIME_TYPE + "=? or "
								+ MediaStore.Images.Media.MIME_TYPE + "=?",
						new String[] { "image/jpeg", "image/png" },
						MediaStore.Images.Media.DATE_MODIFIED);

				Log.e("TAG", mCursor.getCount() + "");
				while (mCursor.moveToNext())
				{
					// 获取图片的路径
					String path = mCursor.getString(mCursor
							.getColumnIndex(MediaStore.Images.Media.DATA));

					Log.e("TAG", path);
					// 拿到第一张图片的路径
					if (firstImage == null)
						firstImage = path;
					// 获取该图片的父路径名
					File parentFile = new File(path).getParentFile();
					if (parentFile == null)
						continue;
					String dirPath = parentFile.getAbsolutePath();
					ImageFloder imageFloder = null;
					// 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
					if (mDirPaths.contains(dirPath))
					{
						continue;
					} else
					{
						mDirPaths.add(dirPath);
						// 初始化imageFloder
						imageFloder = new ImageFloder();
						imageFloder.setDir(dirPath);
						imageFloder.setFirstImagePath(path);
					}

					int picSize = parentFile.list(new FilenameFilter()
					{
						@Override
						public boolean accept(File dir, String filename)
						{
							if (filename.endsWith(".jpg")
									|| filename.endsWith(".png")
									|| filename.endsWith(".jpeg"))
								return true;
							return false;
						}
					}).length;
					//totalCount += picSize;

					imageFloder.setCount(picSize);
					mImageFloders.add(imageFloder);

					if (picSize > mPicsSize)
					{
						mPicsSize = picSize;
						mImgDir = parentFile;
					}
				}
				mCursor.close();

				// 扫描完成，辅助的HashSet也就可以释放内存了
				mDirPaths = null;

				// 通知Handler扫描图片完成
				mHandler.sendEmptyMessage(0x110);

			}
		}).start();

	}

	/**
	 * 初始化View
	 */
	private void initView()
	{
		mGirdView = (GridView) mBaseView.findViewById(R.id.id_gridView);
		//mChooseDir = (TextView) mBaseView.findViewById(R.id.id_choose_dir);
		//mImageCount = (TextView) mBaseView.findViewById(R.id.id_total_count);

		mBottomLy = (RelativeLayout)mBaseView. findViewById(R.id.id_bottom_ly);

	}

//	private void initEvent()
//	{
//		/**
//		 * 为底部的布局设置点击事件，弹出popupWindow
//		 */
//		mBottomLy.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				mListImageDirPopupWindow
//						.setAnimationStyle(R.style.anim_popup_dir);
//				mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
//
//				// 设置背景颜色变暗
//				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
//				lp.alpha = 0f;
//				getActivity().getWindow().setAttributes(lp);
//			}
//		});
//	}

	@Override
	public void selected(ImageFloder floder)
	{

		mImgDir = new File(floder.getDir());
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String filename)
			{
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new ImageAdapter(this.getActivity(), mImgs,
				R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
		// mAdapter.notifyDataSetChanged();
		//mImageCount.setText(floder.getCount() + "张");
		//mChooseDir.setText(floder.getName());
		mListImageDirPopupWindow.dismiss();

	}

	public void selectsize(int count){
		if(count<0){
			count = 0;
		}
		System.out.print(count+"\n");
		//Toast.makeText(getContext(),count,Toast.LENGTH_SHORT).show();
//		TextView fragmentBottom = (FragmentBottom) fragmentManager.findFragmentById(R.id.checkedsize);
//		fragmentBottom.changetext(""+count);
	}

}
