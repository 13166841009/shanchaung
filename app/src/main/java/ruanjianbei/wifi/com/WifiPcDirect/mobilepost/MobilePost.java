package ruanjianbei.wifi.com.WifiPcDirect.mobilepost;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class MobilePost extends Activity {
    private TitleBarView titleBarView;
    private WebView webView;
    private String url = "http://zh749931552.6655.la/thinkphp/Index/file_upload.html";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_post);
        inittitle();
        webView = (WebView) findViewById(R.id.pcwebview);
        webView.loadUrl(url);
    }

    private void inittitle() {
        titleBarView = (TitleBarView) findViewById(R.id.title_bar);
        titleBarView.setCommonTitle(View.VISIBLE,View.VISIBLE,View.GONE,View.GONE);
        titleBarView.setTitleText(R.string.mobilepost);
        titleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        titleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobilePost.this.finish();
            }
        });
    }
}
