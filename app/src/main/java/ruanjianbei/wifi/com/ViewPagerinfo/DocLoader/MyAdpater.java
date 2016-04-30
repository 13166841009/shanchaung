package ruanjianbei.wifi.com.ViewPagerinfo.DocLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

public class MyAdpater extends BaseAdapter {

	/**
	 * 获取存储文件路径的集合
	 */
	private List<String> documentlist;
	private Context context;

	/** 标记CheckBox是否被选中 **/
	private List<Boolean> mChecked;
	/** 存放要显示的Item数据 **/
	private List<String> listPerson;
	/** 一个HashMap对象 **/
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, View> map = new HashMap<Integer, View>();

	public static List<String> mSelectedDocu = FragmentChoose.getFileChoose();

	public MyAdpater(List<String> documentlist,Context context) {
		// TODO 自动生成的构造函数存根
		this.documentlist = documentlist;
		this.context = context;
		listPerson = new ArrayList<String>();
		listPerson = documentlist;
		mChecked = new ArrayList<Boolean>();
		for (int i = 0; i < documentlist.size(); i++) {// 遍历且设置CheckBox默认状态为未选中
			mChecked.add(false);
		}
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return documentlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return documentlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		int positional;
		positional = position;
		ViewHolder viewHolder = null;
		if(map.get(position) ==null){
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.docitem, null);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxdoc);
			viewHolder.docname = (TextView) convertView.findViewById(R.id.docname);
			viewHolder.docpath = (TextView) convertView.findViewById(R.id.docpath);
			final int mposition = position;
			map.put(position, convertView);// 存储视图信息
			viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					mChecked.set(mposition, cb.isChecked());// 设置CheckBox为选中状态
					if(cb.isChecked()){
						mSelectedDocu.add(documentlist.get(position));;
					}else{
						mSelectedDocu.remove(documentlist.get(position));
					}
				}
			});
			convertView.setTag(viewHolder);
		}else{
			convertView = map.get(position);
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//String docname = documentlist.get(position);
		viewHolder.checkBox.setChecked(mChecked.get(position));
		viewHolder.docname.setText(documentlist.get(position).substring(documentlist.get(position).lastIndexOf("/")+1));
//		viewHolder.docname.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(context,"dde",Toast.LENGTH_SHORT).show();
//				notifyDataSetChanged();
//			}
//		});



		return convertView;
	}

	class ViewHolder{
		TextView docname;
		TextView docpath;
		CheckBox checkBox;
	}

}