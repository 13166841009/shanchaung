package ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader.VideoViewInfo;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.IndicatorFragmentActivity;
import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;


public class MusicAdapter extends BaseAdapter{

	private List<MusicInfo> lists;
	private Context context;
	private MediaPlayer mediaPlayer;
	//音乐图标点击事件
	private int firstonclick = 0;

	public static List<String> mSelectedMusci = FragmentChoose.getFileChoose();

	public MusicAdapter(List<MusicInfo> lists, Context context) {
		this.lists = lists;
		this.context = context;
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
		ViewHolder holder = null;
		final int index = position;
		if (convertView == null) {// 根据position判断View是否为空
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.fragment_musicitem, null);
			// 初始化ViewHolder对象
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkboxmusic);
			holder.muscicname = (TextView) convertView.findViewById(R.id.muscicname);
			holder.musicpeople = (TextView) convertView.findViewById(R.id.musicpeople);
			holder.imagemusic = (ImageView) convertView.findViewById(R.id.imagemusic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ViewHolder finalHolder = holder;
		holder.checkBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(finalHolder.checkBox.isChecked()){
					lists.get(index).type = VideoViewInfo.TYPE_CHECKED;
					mSelectedMusci.add(lists.get(index).getMusicdistance());
					IndicatorFragmentActivity.updateBottom();
					notifyDataSetChanged();
				} else {
					lists.get(index).type = VideoViewInfo.TYPE_NOCHECKED;
					mSelectedMusci.remove(lists.get(index).getMusicdistance());
					IndicatorFragmentActivity.updateBottom();
					notifyDataSetChanged();
				}
			}
		});
		if(lists.get(position).type == VideoViewInfo.TYPE_CHECKED){
			holder.checkBox.setChecked(true);
		}else{
			holder.checkBox.setChecked(false);
		}
		holder.muscicname.setText(lists.get(index).getName());
		holder.musicpeople.setText(lists.get(index).getPople());

		final String musicdistance = lists.get(index).getMusicdistance();
		holder.imagemusic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(firstonclick==0){
					Toast.makeText(context, "开始播放：-"+lists.get(index).getName(), Toast.LENGTH_SHORT).show();
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
					Toast.makeText(context, "停止播放：-"+lists.get(index).getName(), Toast.LENGTH_SHORT).show();
					firstonclick = 0;
					mediaPlayer.stop();
				}
			}
		});
		return convertView;
	}
	private static class ViewHolder{
		TextView muscicname;
		TextView musicpeople;
		ImageView imagemusic;
		CheckBox checkBox;
	}
}
