package ruanjianbei.wifi.com.Phone_P_3G.util;

import java.io.File;

/**
 * Created by linankun1 on 2016/4/30.
 */
public class files_delete {
    private String file_path = null;
    private File file = null;
    public files_delete(String file_path){
        this.file_path = file_path;
        file = new File(file_path);
    }
    public  void deleteAll(){
        if(file.isFile() || file.list().length ==0)
        {
            file.delete();
        }else{
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            //if(file.exists())         //如果文件本身就是目录 ，就要删除目录
            //    file.delete();
        }
    }
}
