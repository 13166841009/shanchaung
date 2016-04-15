package ruanjianbei.wifi.com.ViewPagerinfo.MusicLoader;

public class MusicInfo {
	private String musicname;
	private String musicpeople;
	private Boolean ifchoose;
	private String musicdistance;

	public String getMusicdistance() {
		return musicdistance;
	}

	public void setMusicdistance(String musicdistance) {
		this.musicdistance = musicdistance;
	}

	public Boolean getIfchoose() {
		return ifchoose;
	}

	public void setIfchoose(Boolean ifchoose) {
		this.ifchoose = ifchoose;
	}

	public MusicInfo(String musicname,String musicpeople, Boolean ifchoose,String musicdistance) {
		setPople(musicpeople);
		setName(musicname);
		setIfchoose(ifchoose);
		setMusicdistance(musicdistance);
	}

	public String getName() {
		return musicname;
	}
	public void setName(String musicname) {
		this.musicname = musicname;
	}
	public String getPople() {
		return musicpeople;
	}
	public void setPople(String musicpeople) {
		this.musicpeople = musicpeople;
	}



}
