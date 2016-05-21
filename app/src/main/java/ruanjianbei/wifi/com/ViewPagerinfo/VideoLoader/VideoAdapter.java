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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader.MusicInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

public class VideoAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<VideoViewInfo> videoRows;

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
	public static List<String> mSelectedVideo = FragmentChoose.getFileChoose();

	public VideoAdapter(Context context,
						ArrayList<VideoViewInfo> videoRows) {
		this.context = context;
		this.videoRows = videoRows;
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
		final int index = position;
		ViewHolder viewHolder = null;
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pager_fragmentvideo, null);
			// 初始化ViewHolder对象
			viewHolder = new ViewHolder();
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxvedio);
			viewHolder.videoThumb = (ImageView) convertView.findViewById(R.id.imageView);
			viewHolder.videoTitle = (TextView) convertView.findViewById(R.id.videoTitle);
			viewHolder.videoSize = (TextView) convertView.findViewById(R.id.videoSize);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final ViewHolder finalViewHolder = viewHolder;
		viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(finalViewHolder.checkBox.isChecked()){
					videoRows.get(index).type = VideoViewInfo.TYPE_CHECKED;
					mSelectedVideo.add(videoRows.get(index).getFilePath());
				}else{
					videoRows.get(index).type = VideoViewInfo.TYPE_NOCHECKED;
					mSelectedVideo.remove(videoRows.get(index).getFilePath());
				}
			}
		});
		if(videoRows.get(position).type == VideoViewInfo.TYPE_CHECKED){
			viewHolder.checkBox.setChecked(true);
		}else{
			viewHolder.checkBox.setChecked(false);
		}
		if (videoRows.get(position).getThumbPath() != null) {
			viewHolder.videoThumb.setImageURI(Uri.parse(videoRows.get(position).getThumbPath()));
		}
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