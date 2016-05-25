package ruanjianbei.wifi.com.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaItem;

/**
 * 获取文件的大小
 * Created by zhanghang on 2016/5/25.
 */
public class GetFilsSize {

    /**
     * 调用文件传输方法获取文件大小
     * @param path
     * @return
     */
    /**
     * 获取文件的大小
     */
    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件夹不存在");
        }
        return s;
    }
    /**
     * 转换文件大小
     * 将long->String
     * */
    public static String FormentFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS)+"B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024)+"KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576)+"M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824)+"G";
        }
        return fileSizeString;
    }
}
