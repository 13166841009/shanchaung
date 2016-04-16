package ruanjianbei.wifi.com.animation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import activity.WiFiActivity;
import fragment.SettingFragment;
import ruanjianbei.wifi.com.ViewPagerinfo.MainPageActivity;
import ruanjianbei.wifi.com.shanchuang.R;
import ruanjianbei.wifi.com.shanchuang.UserGame;

public class MainPage extends Activity {
    private Context mContext;
    //记录按钮返回次数
     private int count=0;
    /** Called when the activity is first created. */
    MyImageView joke,shouji,dadishu,wo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mContext = this;
        joke=(MyImageView) findViewById(R.id.c_joke);
        joke.setOnClickIntent(new MyImageView.OnViewClick() {

            @Override
            public void onClick() {
                // TODO Auto-generated method stub
                Intent intent=new Intent(MainPage.this, WiFiActivity.class);
                startActivity(intent);
                MainPage.this.overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
            }
        });
        shouji = (MyImageView) findViewById(R.id.c_constellation);
        shouji.setOnClickIntent(new MyImageView.OnViewClick() {

            @Override
            public void onClick() {
//                ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo activieNetWork =  connectivityManager.getActiveNetworkInfo();
//                if(activieNetWork.isConnectedOrConnecting()){
//                    if (activieNetWork.getType()==ConnectivityManager.TYPE_MOBILE){
//                        Toast.makeText(mContext,"3G/4G",Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(mContext, Mobile_PostAndroid.class);
//                        startActivity(intent);
//                        MainPage.this.overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
//                    }else if (activieNetWork.getType()==ConnectivityManager.TYPE_WIFI){
//                        Toast.makeText(mContext,"wifi",Toast.LENGTH_SHORT).show();
//                        Intent intent=new Intent(mContext, Wifi_PostAndroid.class);
//                        startActivity(intent);
//                        MainPage.this.overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
//                    }else {
//                        Toast.makeText(mContext,"蓝牙",Toast.LENGTH_SHORT).show();
//                    }
//                }else{
//                    Toast.makeText(mContext,"断网了",Toast.LENGTH_SHORT).show();
//                }
                Intent intent=new Intent(mContext, MainPageActivity.class);
                startActivity(intent);
                MainPage.this.overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
            }
        });
        dadishu = (MyImageView) findViewById(R.id.c_idea);
        dadishu.setOnClickIntent(new MyImageView.OnViewClick() {

            @Override
            public void onClick() {
                Intent intent = new Intent(mContext, UserGame.class);
                startActivity(intent);
            }
        });
        wo = (MyImageView) findViewById(R.id.c_recommend);
        wo.setOnClickIntent(new MyImageView.OnViewClick() {

            @Override
            public void onClick() {
                Intent intent = new Intent(mContext, SettingFragment.class);
                startActivity(intent);
                MainPage.this.overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(count==0){
                Toast.makeText(MainPage.this,"请再按一次退出",Toast.LENGTH_SHORT).show();
                count++;
            }
            if(count==1){
                Toast.makeText(MainPage.this,"退出",Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
