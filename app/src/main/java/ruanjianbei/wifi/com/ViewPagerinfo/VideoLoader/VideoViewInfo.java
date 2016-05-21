package ruanjianbei.wifi.com.ViewPagerinfo.VideoLoader;

public class VideoViewInfo {
	private String filePath;
	private String mimeType;
	private String thumbPath;
	private String title;
	public static final int TYPE_CHECKED = 1;
	public static final int TYPE_NOCHECKED = 0;

	String name;
	int type;

	public VideoViewInfo(String name,int type){
		this.name = name;
		this.type = type;
	}

	public String getVideosize() {
		return videosize;
	}

	public void setVideosize(String videosize) {
		this.videosize = videosize;
	}

	private String videosize;
	public VideoViewInfo(String filePath,String mimeType, String thumbPath,String title,String videosize) {
		setFilePath(filePath);
		setMimeType(mimeType);
		setThumbPath(thumbPath);
		setTitle(title);
		setVideosize(videosize);
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
