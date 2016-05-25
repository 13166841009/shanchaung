package ruanjianbei.wifi.com.ViewPagerinfo.DocLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.VideoViewInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * create by zhanghang 2016/04/20
 */
public class DocAdpater extends BaseAdapter {

	/**
	 * 获取存储文件路径的集合
	 */
	private List<DocumentInfo> documentlist;
	private Context context;


	public static List<String> mSelectedDocu = FragmentChoose.getFileChoose();

	public DocAdpater(List<DocumentInfo> documentlist, Context context) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.docitem, null);
			viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxdoc);
			viewHolder.docname = (TextView) convertView.findViewById(R.id.docname);
			viewHolder.docpath = (TextView) convertView.findViewById(R.id.docpath);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final ViewHolder finalHolder = viewHolder;
		viewHolder.docname.setText(documentlist.get(position).filepath.substring(documentlist.get(position).filepath.lastIndexOf("/") + 1));
		viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (finalHolder.checkBox.isChecked()){
					documentlist.get(position).type = VideoViewInfo.TYPE_CHECKED;
					mSelectedDocu.add(documentlist.get(position).filepath);
				}else{
					documentlist.get(position).type = VideoViewInfo.TYPE_NOCHECKED;
					mSelectedDocu.remove(documentlist.get(position).filepath);
				}
			}
		});
			if (documentlist.get(position).type==VideoViewInfo.TYPE_CHECKED){
				viewHolder.checkBox.setChecked(true);
			}else{
				viewHolder.checkBox.setChecked(false);
			}
		return convertView;
	}
	class ViewHolder{
		TextView docname;
		TextView docpath;
		CheckBox checkBox;
	}

}