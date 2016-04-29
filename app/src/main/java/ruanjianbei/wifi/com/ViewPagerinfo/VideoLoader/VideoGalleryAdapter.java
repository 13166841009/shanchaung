package ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader.MusicInfo;
import ruanjianbei.wifi.com.shanchuang.R;

public class VideoGalleryAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<VideoViewInfo> videoRows;
	LayoutInflater inflater;

	/** 标记CheckBox是否被选中 **/
	private List<Boolean> mChecked;
	/** 存放要显示的Item数据 **/
	private List<VideoViewInfo> listPerson;
	/** 一个HashMap对象 **/
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, View> map = new HashMap<Integer, View>();

	public VideoGalleryAdapter(Context context,
							   ArrayList<VideoViewInfo> videoRows) {
		this.context = context;
		this.videoRows = videoRows;

		listPerson = new ArrayList<VideoViewInfo>();
		listPerson = videoRows;
		mChecked = new ArrayList<Boolean>();
		for (int i = 0; i < videoRows.size(); i++) {// 遍历且设置CheckBox默认状态为未选中
			mChecked.add(false);
		}
	}

	@Override
	public int getCount() {
		return videoRows.size();
	}

	@Override
	public Object getItem(int position) {
		return videoRows.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		int positional;
		positional = position;
		if(map.get(position)==null){
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pager_fragmentvideo, null);
			viewHolder = new ViewHolder();
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
			viewHolder.videoThumb = (ImageView) convertView.findViewById(R.id.imageView);
			viewHolder.videoTitle = (TextView) convertView.findViewById(R.id.videoTitle);
			viewHolder.videoSize = (TextView) convertView.findViewById(R.id.videoSize);
			final int mposition = position;
			map.put(position, convertView);// 存储视图信息
			viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					mChecked.set(mposition, cb.isChecked());// 设置CheckBox为选中状态
				}
			});
			convertView.setTag(viewHolder);
		}else{
			convertView = map.get(position);
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (videoRows.get(position).getThumbPath() != null) {
			viewHolder.videoThumb.setImageURI(Uri.parse(videoRows.get(position).getThumbPath()));
		}
		viewHolder.checkBox.setChecked(mChecked.get(position));
		viewHolder.videoTitle.setText(videoRows.get(position).getTitle());
		viewHolder.videoSize.setText("大小："+Integer.parseInt(videoRows.get(position).getVideosize())/(1024*1024)+"M "+"类型："+videoRows.get(position).getMimeType());
		return convertView;
	}

	static class ViewHolder{
		ImageView videoThumb;
		TextView videoTitle;
		TextView videoSize;
		CheckBox checkBox;
	}

}