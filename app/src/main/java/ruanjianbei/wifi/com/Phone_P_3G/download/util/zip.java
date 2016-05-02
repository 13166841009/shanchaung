package ruanjianbei.wifi.com.Phone_P_3G.download.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by linankun1 on 2016/4/23.
 */
public class zip {
    public static String ZIP_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/shangchuan/Download/";
    public zip() {
        //构建文件夹
        File dir = new File(ZIP_PATH);
        if (!dir.exists())
        {
            dir.mkdir();
        }
    }
    public boolean jieya(String load){
        Log.i("load路径:", load);
        long startTime=System.currentTimeMillis();
        try {
            ZipInputStream Zip=new ZipInputStream(new FileInputStream(
                    load));//输入源zip路径
            BufferedInputStream Bin=new BufferedInputStream(Zip);
            String Parent=ZIP_PATH; //输出路径（文件夹目录）
            File Fout=null;
            ZipEntry entry;
            try {
                while((entry = Zip.getNextEntry())!=null && !entry.isDirectory()){
                    Fout=new File(Parent,entry.getName());
                    if(!Fout.exists()){
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out=new FileOutputStream(Fout);
                    BufferedOutputStream Bout=new BufferedOutputStream(out);
                    int b;
                    while((b=Bin.read())!=-1){
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    System.out.println(Fout+"解压成功");
                }
                Bin.close();
                Zip.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");
        return true;
    }
}
