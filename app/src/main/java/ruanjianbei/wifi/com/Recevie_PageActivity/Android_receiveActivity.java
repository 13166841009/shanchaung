package ruanjianbei.wifi.com.Recevie_PageActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Utils.WifiConnect.WifiCheck;
import ruanjianbei.wifi.com.shanchuang.R;

public class Android_receiveActivity extends Activity {
    //获取wifi操作的实例
    private WifiCheck wifiCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_receive);
        wifiCheck = new WifiCheck(this);
        if(wifiCheck.isHaveInternet()){
            //需改进，添加wifi连接是否有网络的判断
            if(wifiCheck.isNetworkAvailable()&&wifiCheck.is3G()){
                Toast.makeText(Android_receiveActivity.this,"您将选择有网传输",Toast.LENGTH_LONG).show();
            }else if (wifiCheck.isWifi()){
                Toast.makeText(Android_receiveActivity.this,"您将使用无网络连接或wifi",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Android_receiveActivity.this, downloadActivity.class);
                startActivity(intent);
            }else{
            }
        }else{
            Toast.makeText(Android_receiveActivity.this,"请打开你的wifi或网络连接",Toast.LENGTH_LONG).show();
        }
    }
}
