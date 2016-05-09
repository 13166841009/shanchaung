package ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter;

import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Properties;

import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;

/**
 * 媒体对象
 * @author simon.L
 * @version 1.0.0
 */

/**
 * _ID唯一识别
 * —Data文件的目录地址
 * @author dell-pc
 *
 */
public class MediaItem {
	/**
	 * 添加
	 */
	private ArrayList<String> testlist = (ArrayList<String>) FragmentChoose.getFileChoose();

	public long mId;
	public long mSize;
	public String mData;
	public String mTitle;
	public String mMimeType;

	public MediaItem(Properties properties) {
		try {
			mId = Integer.parseInt(properties.getProperty(MediaStore.Audio.Media._ID));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		try {
			mSize = Integer.parseInt(properties.getProperty(MediaStore.Audio.Media.SIZE));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		mData = properties.getProperty(MediaStore.Audio.Media.DATA);
		mTitle = properties.getProperty(MediaStore.Audio.Media.TITLE);
		mMimeType = properties.getProperty(MediaStore.Audio.Media.MIME_TYPE);
}

	public MediaItem(int i) {
		// TODO 自动生成的构造函数存根
		mId = i;
		mSize = 20000;
		mData = testlist.get(i);
		mTitle = getTitle(mData);
		mMimeType = getType(mData);
	}
	/**
	 * 获取文件名
	 */
	public String getTitle(String path){
		String title;
		int start = path.lastIndexOf("/");
		int end = path.lastIndexOf(".");
		if(start!=-1&&end!=-1){
			title = path.substring(start+1,end);
		}else{
			title = "";
		}
		return title;
	}
	/**
	 * 获取文件的类型
	 */
	public String getType(String path){
		String type;
		type = path.substring(path.lastIndexOf(".")+1);
		return type;
	}
}
