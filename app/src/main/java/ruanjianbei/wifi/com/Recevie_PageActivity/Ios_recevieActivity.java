package ruanjianbei.wifi.com.Recevie_PageActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class Ios_recevieActivity extends Activity {
    private AsyncHttpClient asyncHttpClient;
    private TitleBarView titleBarView;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String url1 = "http://192.168.23.4:8080/download?path=%2F20160530154503.png";
    private Button posttest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ios_recevie);
        inittile();
        posttest = (Button) findViewById(R.id.testrecevie);
        posttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(url1,new FileAsyncHttpResponseHandler(getApplicationContext()){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, File file) {
                        System.out.println(path);
                        copyFile(file,path);
//                        System.out.println(file.getAbsolutePath() + ", " + file.getName() + ", " + file.getPath());
//                        System.out.println(file.toString());
                    }
                });
            }
        });
    }
    public void copyFile(File oldFile, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldFile); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath + "/test.png");
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }
    private void inittile() {
        titleBarView = (TitleBarView) findViewById(R.id.title_bar);
        titleBarView.setCommonTitle(View.VISIBLE,View.VISIBLE,View.GONE,View.GONE);
        titleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        titleBarView.setTitleText(R.string.iospost);
    }
}
