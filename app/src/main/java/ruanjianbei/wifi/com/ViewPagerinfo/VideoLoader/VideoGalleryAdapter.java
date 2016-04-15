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
		View videoRow = inflater.inflate(R.layout.pager_fragmentvideo, null);
		ImageView videoThumb = (ImageView) videoRow.findViewById(R.id.imageView);
		if (videoRows.get(position).getThumbPath() != null) {
			videoThumb.setImageURI(Uri.parse(videoRows.get(position).getThumbPath()));
		}
		TextView videoTitle = (TextView) videoRow.findViewById(R.id.textView);
		videoTitle.setText(videoRows.get(position).getTitle());
		return videoRow;
	}

}
