package ruanjianbei.wifi.com.ViewPagerinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.GetDocument;
import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.MyAdpater;
import ruanjianbei.wifi.com.shanchuang.R;

public class FragmentWord extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_word, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listview = (ListView) getActivity().findViewById(R.id.listview);
		GetDocument.GetDocument(getContext());
		MyAdpater Adpater = new MyAdpater(GetDocument.documentlist, getContext());
		listview.setAdapter(Adpater);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO 自动生成的方法存根
				System.out.println(position);
			}
		});
	}
}

