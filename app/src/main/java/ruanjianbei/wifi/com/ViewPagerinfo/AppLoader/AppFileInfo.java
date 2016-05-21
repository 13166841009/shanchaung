package ruanjianbei.wifi.com.ViewPagerinfo.AppLoader;


/**
 * Created by 郭攀峰 on 2015/9/16.
 * android设备中的文件
 */
public class AppFileInfo
{
    public String path;
    public String name;
    public long size;
    public int type;
    public int percent;
    public boolean success;
    public long LengthNeeded = 0;

    public AppFileInfo()
    {

    }

    public int getPercent()
    {
        return percent;
    }

    public void setPercent(int percent)
    {
        this.percent = percent;
        if (percent == 100)
        {
            success = true;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        return (((AppFileInfo) (o)).name.equals(name))
            && (((AppFileInfo) (o)).size == size) && (((AppFileInfo) (o)).type == type)
            && (((AppFileInfo) (o)).path.equals(path));
    }

    @Override
    public String toString()
    {
        return name + ":" + size + ":" + type + "\0";
    }

    public AppFileInfo duplicate()
    {
        AppFileInfo file = new AppFileInfo();

        file.name = this.name;
        file.size = this.size;
        file.path = this.path;
        file.type = this.type;
        file.percent = this.percent;
        file.success = this.success;
        file.LengthNeeded = this.LengthNeeded;

        return file;
    }
}
