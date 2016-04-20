package ruanjianbei.wifi.com.ViewPagerinfo.DocLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.Toast;

public class GetDocument {
	private static Context mcontext;
	public static List<String> documentlist = new ArrayList<String>();
	/**
	 * 根据条件获取相应的文件
	 */
	private static String [] DocFormatSet = new String[] { ".docx",".doc"}; // 合法的音频文件格式
	private static String sdpath = "/sdcard/";

	public static String GetDocument(Context context){
		mcontext = context;
		getFiles(sdpath);
		return null;
	}

	private static void getFiles(String url) {
		File files = new File(url); // 创建文件对象
		File[] file = files.listFiles();
		if(file.length!=0){
			try {
				for (File f : file) {
					if (f.isDirectory()) { // 如果是目录，也就是文件夹
						getFiles(f.getAbsolutePath()); // 递归调用
					} else{
						if (isDocFile(f.getPath())) { // 如果是音频文件
							documentlist.add(f.getPath());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace(); // 输出异常信息
			}
		}
	}

	private static boolean isDocFile(String path) {
		for (String format : DocFormatSet) { // 遍历数组
			if (path.contains(format)) { // 判断是否为有合法的音频文件
				return true;
			}
		}
		return false;
	}
}
