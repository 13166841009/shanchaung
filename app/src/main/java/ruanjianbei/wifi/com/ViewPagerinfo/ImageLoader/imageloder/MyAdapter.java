package ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.imageloder;

import android.content.Context;
import android.graphics.Color;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.FragmentPhoto;
import ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.utils.CommonAdapter;
import ruanjianbei.wifi.com.ViewPagerinfo.ImageLoader.utils.ViewHolder;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;


public class MyAdapter extends CommonAdapter<String>
{
	//private Context context;
	/**
	 * 得到文件数量
	 */
	private FragmentPhoto fragmentPhoto = new FragmentPhoto();
	private int countphoto = 0;

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedImage = new LinkedList<String>();

	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
					 String dirPath)
	{
		super(context,mDatas,itemLayoutId);
		//this.context = context;
		this.mDirPath = dirPath;
	}

	@Override
	public void convert(final ViewHolder helper, final String item)
	{
		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.mipmap.pictures_no);
		//设置no_selected
		helper.setImageResource(R.id.id_item_select,
				R.mipmap.picture_unselected);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);

		mImageView.setColorFilter(null);
		//设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener()
		{
			//选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v)
			{
				// 已经选择过该图片
				if (mSelectedImage.contains(mDirPath + "/" + item))
				{
					countphoto--;
					fragmentPhoto.selectsize(countphoto);
					Toast.makeText(mContext, "不选了", Toast.LENGTH_SHORT).show();
					mSelectedImage.remove(mDirPath + "/" + item);
					mSelect.setImageResource(R.mipmap.picture_unselected);
					mImageView.setColorFilter(null);
				} else
				// 未选择该图片
				{
					countphoto++;
					fragmentPhoto.selectsize(countphoto);
					//System.out.print(countphoto);
					Toast.makeText(mContext, mDirPath + "/" + item, Toast.LENGTH_SHORT).show();
					System.out.println(mDirPath + "/" + item);
					mSelectedImage.add(mDirPath + "/" + item);
					mSelect.setImageResource(R.mipmap.pictures_selected);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
				}

			}
		});

		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (mSelectedImage.contains(mDirPath + "/" + item))
		{
			mSelect.setImageResource(R.mipmap.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

		/**
		 * 将已选图片的路径传入FragmentChoose中的filechoose集合中
		 */
		FragmentChoose.setFileChoose(mSelectedImage);

	}
}
