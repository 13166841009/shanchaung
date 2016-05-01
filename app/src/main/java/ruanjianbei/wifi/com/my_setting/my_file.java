package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class my_file extends Activity{
    private TextView mReceiver;
    private String content = "我的文件";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_file);
        mReceiver = (TextView) findViewById(R.id.mTextView);
        mReceiver.setText(content);
    }
}

