package ruanjianbei.wifi.com.ViewPagerinfo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.GetVideoInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.VideoAdapter;
import ruanjianbei.wifi.com.shanchuang.R;

public class FragmentVideo extends Fragment {
	private ListView listView;
	private Cursor cursor;
//	private String[] mediaColumns = { MediaStore.Video.Media._ID,
//			MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
//			MediaStore.Video.Media.MIME_TYPE };
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_video, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GetVideoInfo.getvideo(getContext());
//		cursor = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//				mediaColumns, null, null, null);
		listView = (ListView) getActivity().findViewById(R.id.listView);
		listView.setAdapter(new VideoAdapter(getContext(), GetVideoInfo.videoRows));
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				if (cursor.moveToPosition(position)) {
//					/**
//					 * 这个方法将从Cursor对象中提取所需的数据，点击一个item，将创建一个意图，以启动手机设备上默认的媒体播放器来播放该item视频。
//					 */
//					int fileColumn = cursor
//							.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
//					int mimeColumn = cursor
//							.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE);
//					String videoFilePath = cursor.getString(fileColumn);
//					String mimeType = cursor.getString(mimeColumn);
//					Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//					File newFile = new File(videoFilePath);
//					intent.setDataAndType(Uri.fromFile(newFile), mimeType);
//					startActivity(intent);
//				}
//			}
//		});
	}
}
