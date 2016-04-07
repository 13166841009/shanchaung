package ruanjianbei.wifi.com.shanchuang;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Mobile_PostAndroid extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_postandroid);
    }

    public void click(){
        Uri uri = Uri.parse("http://zh749931552.6655.la/ThinkPHP/index.php/Index/file_load.html");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
}
