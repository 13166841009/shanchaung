package ruanjianbei.wifi.com.Recevie_PageActivty.RecevieMain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ruanjianbei.wifi.com.Recevie_PageActivty.Android_receiveActivity;
import ruanjianbei.wifi.com.Recevie_PageActivty.Ios_recevieActivity;
import ruanjianbei.wifi.com.shanchuang.R;

public class ReceiveActivity extends Activity {

    private Button android_receive;
    private Button ios_receive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        android_receive = (Button) findViewById(R.id.android_receive);
        ios_receive = (Button) findViewById(R.id.ios_recevie);
        android_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceiveActivity.this, Android_receiveActivity.class));
            }
        });

        ios_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceiveActivity.this, Ios_recevieActivity.class));
            }
        });
    }
}
