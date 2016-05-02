package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        String content = new diolog().getcontent(my_information.this, tv1);
        Toast.makeText(my_information.this, content, Toast.LENGTH_SHORT).show();
        tv1.setText(content);
    }
}



