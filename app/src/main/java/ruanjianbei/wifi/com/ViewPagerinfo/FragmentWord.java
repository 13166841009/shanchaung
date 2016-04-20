package ruanjianbei.wifi.com.ViewPagerinfo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.GetDocument;
import ruanjianbei.wifi.com.ViewPagerinfo.DocLoader.MyAdpater;
import ruanjianbei.wifi.com.shanchuang.R;

public class FragmentWord extends Fragment {
	private ListView listview;
	private MyAdpater Adpater;
	/**
	 * 程序一个Handler，用来接收进程传过来的信息
	 * 然后设置ListView的点击事件
	 */
	private android.os.Handler mHandler = new android.os.Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what==1){
				Adpater = new MyAdpater(GetDocument.documentlist, getContext());
				listview.setAdapter(Adpater);
				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						System.out.print(position);
					}
				});
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

