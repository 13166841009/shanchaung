package ruanjianbei.wifi.com.ViewPagerinfo;

import ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader.GetMusic;
import ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader.MusicAdapter;
import ruanjianbei.wifi.com.shanchuang.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class FragmentMusic extends Fragment {
	private ListView lv;
	private MusicAdapter adapter;
	private ImageView image;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_music, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GetMusic.getNumber(getContext());
//      image = (ImageView) findViewById(R.id.imagemusic);
		lv = (ListView) getActivity().findViewById(R.id.lv);
		adapter = new MusicAdapter(GetMusic.lists, getContext());
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO 自动生成的方法存根
				System.out.println(position);
			}
		});
	}
}
