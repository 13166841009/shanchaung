package ruanjianbei.wifi.com.Iospost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.luoxudong.app.asynchttp.AsyncHttpRequest;
import com.luoxudong.app.asynchttp.AsyncHttpUtil;
import com.luoxudong.app.asynchttp.callable.UploadRequestCallable;
import com.luoxudong.app.asynchttp.model.FileWrapper;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruanjianbei.wifi.com.ViewPagerinfo.ui.filechoose.FragmentChoose;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class IosPost_Activity extends Activity {
    private AsyncHttpClient asyncHttpClient;
    private TitleBarView titleBarView;
    private AsyncHttpRequest asyncHttpRequest01;
    private String URCODE = null;
    private FileWrapper fileWrapper;
    private Button posttest;
    private Map<String, FileWrapper> fileWrappers;
    private List<String> iospostFile = FragmentChoose.getFileChoose();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ios_post_wifi);
        inittile();
        URCODE = geturl();
        final String urlapply = URCODE+"uploadApply";
        final String urlupload = URCODE+"upload";
        posttest = (Button) findViewById(R.id.testpost);
        posttest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncHttpClient = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("type", "image/png");
                asyncHttpClient.post(urlapply, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(IosPost_Activity.this, "传输成功", Toast.LENGTH_SHORT).show();
                        fileWrappers = new HashMap<String, FileWrapper>();//初始化Map集合
                        fileWrapper = new FileWrapper();
                        fileWrapper.setFile(new File(iospostFile.get(0)));
                        fileWrappers.put("files[]", fileWrapper);
                        asyncHttpRequest01 = new AsyncHttpUtil.Builder()
                                .url(urlupload)
                                .setFileWrappers(fileWrappers)
                                .setCallable(new UploadRequestCallable() {
                                    @Override
                                    public void onFailed(int i, String s) {
                                        Toast.makeText(IosPost_Activity.this, "传输失败", Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onSuccess(Header[] headers, List<Cookie> cookies) {
                                        Toast.makeText(IosPost_Activity.this, "传输成功", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .build().upload();
                    }
                });
            }
        });
    }
    private void inittile() {
        titleBarView = (TitleBarView) findViewById(R.id.title_bar);
        titleBarView.setCommonTitle(View.VISIBLE,View.VISIBLE,View.GONE,View.GONE);
        titleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        titleBarView.setTitleText(R.string.iospost);
    }
    public String geturl() {
        Intent intent = getIntent();
        String recode = intent.getStringExtra("recode");
        return recode;
    }
}
