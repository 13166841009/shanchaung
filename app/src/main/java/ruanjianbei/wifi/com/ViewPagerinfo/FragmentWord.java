package ruanjianbei.wifi.com.ViewPagerinfo;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.GetDocument;
import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.DocAdpater;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;
import ruanjianbei.wifi.com.shanchuang.R;
import util.CustomProgressDialog;

public class FragmentWord extends Fragment {
	private ListView listview;
	private TextView textView;
	private DocAdpater Adpater;
	private static CustomProgressDialog customProgressDialog = null;
	/**
	 * 程序一个Handler，用来接收进程传过来的信息
	 * 然后设置ListView的点击事件
	 */
	private android.os.Handler mHandler = new android.os.Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				textView.setVisibility(View.GONE);
				Adpater = new DocAdpater(GetDocument.documentlist, getContext());
				listview.setAdapter(Adpater);
			}
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_word, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findView();
		initseeting();
	}
	/**
	 * 加载视图
	 */
	private void findView() {
		textView  = (TextView) getActivity().findViewById(R.id.waitdoc);
		listview = (ListView) getActivity().findViewById(R.id.wordlistview);
	}
	public void initseeting(){
		new Thread(){
			@Override
			public void run() {
				GetDocument.GetDocument(getContext());
				mHandler.sendEmptyMessage(1);
			}
		}.start();
	}
}

