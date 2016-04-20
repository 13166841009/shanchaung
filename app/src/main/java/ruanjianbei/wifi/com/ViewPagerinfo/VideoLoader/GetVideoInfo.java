package ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class GetVideoInfo {
	/**
	 * 从MediaStore.Video.Thumbnail查询中获得的列的列表。
	 */
	private static String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
			MediaStore.Video.Thumbnails.VIDEO_ID };
	/**
	 * 从MediaStore.Video.Media查询中获得的列的列表。
	 */
	private static String[] mediaColumns = { MediaStore.Video.Media._ID,
			MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE
			,MediaStore.Video.Media.SIZE,
			MediaStore.Video.Media.MIME_TYPE };
	public static ArrayList<VideoViewInfo> videoRows = new ArrayList<VideoViewInfo>();
	/**
	 * 在主查询中将选择所有在MediaStore中表示的视频
	 */
	public static String getvideo(Context context){
		String thumbPath = null;
		Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				mediaColumns, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				/**
				 * 将使用另一个查询为每个视频提取缩略图，而且这些数据块都将存储在VideoViewInfo对象中。
				 */
				int id = cursor.getInt(cursor
						.getColumnIndex(MediaStore.Video.Media._ID));
				Cursor thumbCursor = context.getContentResolver().query(
						MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
						thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
								+ "=" + id, null, null);
				if (thumbCursor.moveToFirst()) {
					thumbPath = thumbCursor.getString(thumbCursor
							.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
				}
				String videosize = cursor.getString(cursor
						.getColumnIndex(MediaStore.Video.Media.SIZE));
				String filePath = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
				String title = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
				String mimeType = cursor
						.getString(cursor
								.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
				VideoViewInfo videoViewInfo = new VideoViewInfo(filePath, mimeType, thumbPath, title,videosize);
				videoRows.add(videoViewInfo);
			} while (cursor.moveToNext());
		}
		return null;
	}

}
