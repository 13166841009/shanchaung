package ruanjianbei.wifi.com.ViewPagerinfo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.VideoViewInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * create by zhanghang 2016/05/25
 */
public class FragmentOthers extends Fragment {
	private ListView listview;
	private TextView textview;
	public static List<String> mSelectFile = FragmentChoose.getFileChoose();
	//记录当前父文件夹
	File currentParent;
	File [] currentFiles;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_others, container,false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listview = (ListView) getActivity().findViewById(R.id.list);
		textview = (TextView) getActivity().findViewById(R.id.path);
		//获取系统SD卡的目录
		final String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File root = new File(sdpath);
		if(root.exists()){
			currentParent = root;
			currentFiles = root.listFiles();
			//使用当前目录下的全部文件、文件夹来填充ListView
			if(currentFiles!=null) {
				inflateListView(currentFiles);
			}
		}
		//weilistview绑定列表项的点击事件的监控器
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				//获取用户单击的文件
				if(currentFiles[position].isFile())
					return;
				File[] tmp = currentFiles[position].listFiles();
				if(tmp ==null||tmp.length==0){
					Toast.makeText(getContext(),
							"当前路径不可访问或改路径没有文件", Toast.LENGTH_SHORT).show();
				}else{
					//获取用户点击的列表对的文件夹，设定为当前的父文件夹
					currentParent = currentFiles[position];
					//保存当前父文件夹内的全部文件和文件夹
					currentFiles = tmp;
					//再次更新ListView
					inflateListView(currentFiles);
				}
			}
		});
		//获取上一级目录按钮
		final Button parent = (Button) getActivity().findViewById(R.id.parent);
		parent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				try {
					if (currentParent.getCanonicalPath().equals(sdpath)) {
						Toast.makeText(getActivity(),"到底啦...",Toast.LENGTH_SHORT).show();
					}else{
						//获取上一级的目录
						currentParent = currentParent.getParentFile();
						//列出当前目录下的所有文件
						currentFiles = currentParent.listFiles();
						//再次更新ListView
						inflateListView(currentFiles);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 *获取子文件下的目录
	 */
	private void inflateListView(final File[] files) {
		//创建一个List集合，List集合的元素是Map
		final List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
		for(int i = 0;i<files.length;i++){
			Map<String,Object> listItem = new HashMap<String,Object>();
			//如果当前Files是文件夹，使用folder图标;否则使用file图标
			if(files[i].isDirectory()){
				listItem.put("icon", R.mipmap.file_image);
			}else{
				listItem.put("icon", R.mipmap.file_image);
			}
			if(files[i].getName().substring(files[i].getName().lastIndexOf(".")+1).equals("doc")||
					files[i].getName().substring(files[i].getName().lastIndexOf(".")+1).equals("docx")){
				listItem.put("icon", R.mipmap.file_doc);
			}else if(files[i].getName().substring(files[i].getName().lastIndexOf(".")+1).equals("xls")){
				listItem.put("icon",R.mipmap.file_xls);
			}else if(files[i].getName().substring(files[i].getName().lastIndexOf(".")+1).equals("pdf")){
				listItem.put("icon",R.mipmap.file_pdf);
			}else if(files[i].getName().substring(files[i].getName().lastIndexOf(".")+1).equals("apk")){
				listItem.put("icon",R.mipmap.file_apk);
			}else if(files[i].getName().substring(files[i].getName()
					.lastIndexOf(".")+1).equals("jpg")){
				listItem.put("icon",R.mipmap.file_jpg);
			}else if(files[i].getName().substring(files[i].getName()
					.lastIndexOf(".")+1).equals("png")){
				listItem.put("icon",R.mipmap.file_png);
			}else{
				listItem.put("icon",R.mipmap.file_image);
			}
			listItem.put("filename", files[i].getName());
			//添加List项
			listItems.add(listItem);
		}
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapter =
				new SimpleAdapter(getContext(), listItems, R.layout.file_fragment_item,
						new String[]{"icon","filename"}, new int[]{R.id.icone,R.id.file_name}){
					@Override
					public View getView(final int position, View convertView, ViewGroup parent) {
						View view = super.getView(position, convertView, parent);
						@SuppressWarnings("unchecked")
						//获取相应View中的Checkbox对象
								CheckBox checkBox = (CheckBox)view.findViewById(R.id.filecheck);
						checkBox.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								if(((CheckBox)view).isChecked()){
									listItems.get(position).put("type",VideoViewInfo.TYPE_CHECKED);
									mSelectFile.add(files[position].getAbsolutePath());
									IndicatorFragmentActivity.updateBottom();
									notifyDataSetChanged();
								}else{
									listItems.get(position).put("type",VideoViewInfo.TYPE_NOCHECKED);
									mSelectFile.remove(files[position].getAbsolutePath());
									IndicatorFragmentActivity.updateBottom();
									notifyDataSetChanged();
								}
							}
						});
//						if(listItems.get(position).get("type") == VideoViewInfo.TYPE_CHECKED){
//							checkBox.setChecked(true);
//						}else{
//							checkBox.setChecked(false);
//						}
						return view;
					}
				};
		//为ListView设置adapter
		listview.setAdapter(simpleAdapter);
		try {
			textview.setText("当前路径为：" + currentParent.getCanonicalPath());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

}
