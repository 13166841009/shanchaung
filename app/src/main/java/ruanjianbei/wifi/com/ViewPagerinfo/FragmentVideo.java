package ruanjianbei.wifi.com.ViewPagerinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.GetVideoInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.VideoAdapter;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * create by zhanghang 2016/04/15
 */
public class FragmentVideo extends Fragment {
	private ListView listView;
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
		listView = (ListView) getActivity().findViewById(R.id.listView);
		listView.setAdapter(new VideoAdapter(getContext(), GetVideoInfo.videoRows));
	}
}
