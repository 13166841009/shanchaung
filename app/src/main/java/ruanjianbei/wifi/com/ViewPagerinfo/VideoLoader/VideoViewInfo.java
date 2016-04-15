package ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader;

public class VideoViewInfo {
	private String filePath;
	private String mimeType;
	private String thumbPath;
	private String title;
	public VideoViewInfo(String filePath,String mimeType, String thumbPath,String title) {
		setFilePath(filePath);
		setMimeType(mimeType);
		setThumbPath(thumbPath);
		setTitle(title);
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public String getThumbPath() {
		return thumbPath;
	}
	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
