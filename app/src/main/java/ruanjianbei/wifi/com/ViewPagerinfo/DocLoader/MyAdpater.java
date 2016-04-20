package ruanjianbei.wifi.com.ViewPagerinfo.DocLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ruanjianbei.wifi.com.shanchuang.R;

public class MyAdpater extends BaseAdapter {

	/**
	 * 获取存储文件路径的集合
	 */
	private List<String> documentlist = new ArrayList<String>();
	private Context context;
	public MyAdpater(List<String> documentlist,Context context) {
		// TODO 自动生成的构造函数存根
		this.documentlist = documentlist;
		this.context = context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder;
		if(convertView==null){
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.docitem, null);
			viewHolder.docname = (TextView) convertView.findViewById(R.id.docname);
			viewHolder.docpath = (TextView) convertView.findViewById(R.id.docpath);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		//String docname = documentlist.get(position);
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
	}

}
