package ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;


public class MusicAdapter extends BaseAdapter{

	private List<MusicInfo> lists;
	private Context context;
	private static int thisPositon;
	private LinearLayout layout;
	private MediaPlayer mediaPlayer;

	/** 标记CheckBox是否被选中 **/
	private List<Boolean> mChecked;
	/** 存放要显示的Item数据 **/
	private List<MusicInfo> listPerson;
	/** 一个HashMap对象 **/
	@SuppressLint("UseSparseArrays")
	private HashMap<Integer, View> map = new HashMap<Integer, View>();
	//音乐图标点击事件
	private int firstonclick = 0;

    public static List<String> mSelectedMusci = FragmentChoose.getFileChoose();

	public MusicAdapter(List<MusicInfo> lists, Context context) {
		this.lists = lists;
		this.context = context;
		listPerson = new ArrayList<MusicInfo>();
		listPerson = lists;
		mChecked = new ArrayList<Boolean>();
		for (int i = 0; i < lists.size(); i++) {// 遍历且设置CheckBox默认状态为未选中
			mChecked.add(false);
		}
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		int positional;
		positional = position;
		ViewHolder holder = null;
		if (map.get(position) == null) {// 根据position判断View是否为空
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mInflater.inflate(R.layout.fragment_musicitem, null);
			// 初始化ViewHolder对象
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) view.findViewById(R.id.checkboxmusic);
			holder.muscicname = (TextView) view.findViewById(R.id.muscicname);
			holder.musicpeople = (TextView) view.findViewById(R.id.musicpeople);
			holder.imagemusic = (ImageView) view.findViewById(R.id.imagemusic);
			final int mposition = position;
			map.put(position, view);// 存储视图信息
			holder.checkBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					mChecked.set(mposition, cb.isChecked());// 设置CheckBox为选中状态
                    /**
                     * 存储已经选择文件路径
                     */
					if(cb.isChecked()){
                        mSelectedMusci.add(listPerson.get(position).getMusicdistance());
					}else{
                        mSelectedMusci.remove(listPerson.get(position).getMusicdistance());
                    }

				}
			});
			view.setTag(holder);
		} else {
			view = map.get(position);
			holder = (ViewHolder) view.getTag();
		}

		holder.checkBox.setChecked(mChecked.get(position));
		holder.muscicname.setText(listPerson.get(position).getName());
		holder.musicpeople.setText(listPerson.get(position).getPople());

		final String musicdistance = lists.get(positional).getMusicdistance();
		holder.imagemusic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(firstonclick==0){
					Toast.makeText(context, musicdistance, Toast.LENGTH_SHORT).show();
					mediaPlayer = new MediaPlayer();
					try {
						mediaPlayer.reset();
						mediaPlayer.setDataSource(musicdistance);
						mediaPlayer.prepare();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					firstonclick=1;
					mediaPlayer.start();
				}else{
					firstonclick = 0;
					mediaPlayer.stop();
				}
			}
		});
		return view;
	}
	private static class ViewHolder{
		TextView muscicname;
		TextView musicpeople;
		ImageView imagemusic;
		CheckBox checkBox;
	}
}
