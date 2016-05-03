package ruanjianbei.wifi.com.my_setting.util;

import android.os.Environment;
public class Tools {
	public static boolean hasSdcard(){
		//检测是否有sd卡
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
}
