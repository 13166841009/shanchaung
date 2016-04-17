package ruanjianbei.wifi.com.ViewPagerinfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.GetDocument;
import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.MyAdpater;
import ruanjianbei.wifi.com.shanchuang.R;

public class FragmentWord extends Fragment {
	private ListView listview;
	private MyAdpater Adpater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_word, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listview = (ListView) getActivity().findViewById(R.id.wordlistview);
		MyAsync myAsync = new MyAsync();
		myAsync.execute();
		Toast.makeText(getContext(),"加载中",Toast.LENGTH_SHORT).show();
		Adpater = new MyAdpater(GetDocument.documentlist, getContext());
		listview.setAdapter(Adpater);
	}

	private class MyAsync extends AsyncTask<String,Integer,String>{

		@Override
		protected void onPreExecute() {
			Log.i("我的闪传", "onPreExecute() called");
			Toast.makeText(getContext(),"加载前",Toast.LENGTH_SHORT).show();
		}

		@Override
		protected String doInBackground(String... params) {
			GetDocument.GetDocument(getContext());
			return null;
		}

		@Override
		protected void onPostExecute(String s) {
			Toast.makeText(getContext(),"加载完成",Toast.LENGTH_SHORT).show();
		}
	}
}

