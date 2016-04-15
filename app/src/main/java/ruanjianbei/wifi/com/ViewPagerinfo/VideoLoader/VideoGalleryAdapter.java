package ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;

public class VideoGalleryAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<VideoViewInfo> videoRows;
	LayoutInflater inflater;
	public VideoGalleryAdapter(Context context,
							   ArrayList<VideoViewInfo> videoRows) {
		this.context = context;
		this.videoRows = videoRows;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return videoRows.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.pager_fragmentvideo, null);
			viewHolder = new ViewHolder();
			viewHolder.videoThumb = (ImageView) convertView.findViewById(R.id.imageView);
			viewHolder.videoTitle = (TextView) convertView.findViewById(R.id.textView);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (videoRows.get(position).getThumbPath() != null) {
			viewHolder.videoThumb.setImageURI(Uri.parse(videoRows.get(position).getThumbPath()));
		}
		viewHolder.videoTitle.setText(videoRows.get(position).getTitle());
		return convertView;
	}

	static class ViewHolder{
		ImageView videoThumb;
		TextView videoTitle;
	}

}
