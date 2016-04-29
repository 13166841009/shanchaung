package ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.guo.duoduo.randomtextview.RandomTextView;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils.WifiAdmin;
import ruanjianbei.wifi.com.shanchuang.R;

public class RecevieByWifi extends Activity {
    private WifiAdmin wifiAdmin;
    //当前应用是否开启wifi
    public Boolean ifopenwifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recevie_wifi);
        wifiAdmin = new WifiAdmin(this);
        if(wifiAdmin.isNetCardFrindly()){
            Toast.makeText(RecevieByWifi.this,"您的wifi已经开启",Toast.LENGTH_SHORT).show();
        }else{
            wifiAdmin.openNetCard();
        }
        final RandomTextView randomTextView = (RandomTextView) findViewById(
                R.id.random_textview);
        randomTextView.setOnRippleViewClickListener(
                new RandomTextView.OnRippleViewClickListener() {
                    @Override
                    public void onRippleViewClicked(View view) {
                        RecevieByWifi.this.startActivity(
                                new Intent(RecevieByWifi.this, downloadActivity.class));
                    }
                });

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                randomTextView.addKeyWord("张行");
                randomTextView.addKeyWord("习近平");
                randomTextView.show();
            }
        }, 2 * 1000);
    }
}
