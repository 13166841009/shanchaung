package ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter;

import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
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
	public String size;

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


		File ff = new File(mData);

		try {
			mSize = Long.parseLong(FormentFileSize(getFileSizes(ff)));
		} catch (IOException e) {
			e.printStackTrace();
		}

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

	public long getFileSizes(File f) throws IOException {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
			fis.close();
		} else {
			f.createNewFile();
			System.out.println("文件夹不存在");
		}
		return s;
	}

//	/**
//	 * 递归
//	 * */
//	public long getFileSize(File f) {
//		long size = 0;
//		File flist[] = f.listFiles();
//		for (int i = 0; i < flist.length; i++) {
//			if (flist[i].isDirectory()) {
//				size = size + getFileSize(flist[i]);
//			} else {
//				size = size + flist[i].length();
//			}
//		}
//		return size;
//	}

	/**
	 * 转换文件大小
	 **/
	public String FormentFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
}
