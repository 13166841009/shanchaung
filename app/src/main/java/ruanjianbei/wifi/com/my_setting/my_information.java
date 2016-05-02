package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ruanjianbei.wifi.com.my_setting.util.diolog;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class my_information extends Activity{
    private TextView tv1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        tv1 = (TextView) findViewById(R.id.nicheng);
    }
    public void onClicknicheng(View v){
        tv1.setText(new diolog().getcontent(my_information.this));
    }
}



