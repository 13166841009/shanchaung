package ruanjianbei.wifi.com.Phone_P_3G.download.entities;

import java.io.Serializable;

/**
 * Created by linankun1 on 2016/4/23.
 */
public class FileInfo implements Serializable
{
    private int id;
    private String url;
    private String fileName;
    private String trueName;
    private int length;
    private int finished;

    /**
     *@param id
     *@param url
     *@param fileName
     *@param length
     *@param finished
     */
    public FileInfo(int id, String url, String fileName,String trueName, int length,
                    int finished)
    {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.trueName = trueName;
        this.length = length;
        this.finished = finished;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getUrl()
    {
        return url;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    public String getFileName()
    {
        return fileName;
    }
    public void setFileName(String trueName)
    {
        this.fileName = fileName;
    }
    public String gettrueName()
    {
        return trueName;
    }
    public void settrueName(String fileName)
    {
        this.trueName = trueName;
    }
    public int getLength()
    {
        return length;
    }
    public void setLength(int length)
    {
        this.length = length;
    }
    public int getFinished()
    {
        return finished;
    }
    public void setFinished(int finished)
    {
        this.finished = finished;
    }
    @Override
    public String toString()
    {
        return "FileInfo [id=" + id + ", url=" + url + ", fileName=" + fileName
                + ", length=" + length + ", finished=" + finished + "]";
    }


}
