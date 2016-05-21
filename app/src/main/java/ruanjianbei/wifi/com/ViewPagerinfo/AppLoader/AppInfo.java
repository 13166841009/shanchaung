package ruanjianbei.wifi.com.ViewPagerinfo.AppLoader;


import android.graphics.drawable.Drawable;


/**
 * Created by 郭攀峰 on 2015/9/15.
 */
public class AppInfo implements InterfaceAppInfo
{
    public int type = 0;
    public Drawable appIcon;
    public String appLabel;
    public String pkgName;
    public String appSize;
    public String appFilePath;

    @Override
    public String getFilePath()
    {
        return appFilePath;
    }

    @Override
    public String getFileSize()
    {
        return appSize;
    }

    @Override
    public int getFileType()
    {
        return type;
    }

    @Override
    public Drawable getFileIcon()
    {
        return appIcon;
    }

    @Override
    public String getFileName()
    {
        return appLabel;
    }

    @Override
    public boolean equals(Object o)
    {
        if (getFilePath() != null && ((AppInfo) o).getFilePath() != null)
            return getFilePath().equals(((AppInfo) o).getFilePath());
        else
            return false;
    }

}
