package ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.ImageView;

public class GetMusic {
	private static Boolean  imchoose;
	public static Boolean getImchoose() {
		return imchoose;
	}

	public static void setImchoose(Boolean imchoose) {
		GetMusic.imchoose = imchoose;
	}

	public static List<MusicInfo> lists = new ArrayList<MusicInfo>();

	public static String getNumber(Context context){
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.
				EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Media.ARTIST);
		String musicname;
		String musicpeople;
		String musicdistance;
		while (cursor.moveToNext()) {
			musicname = cursor.getString(cursor.
	    			getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
			musicpeople = cursor.getString(cursor
	    			.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
			musicdistance = cursor.getString(cursor
	    			.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			imchoose = true;
			MusicInfo phoneInfo = new MusicInfo(musicname, musicpeople,imchoose,musicdistance);
			lists.add(phoneInfo);
//			System.out.println(phoneName+phoneNumber);
		}
		return null;
	}

}
