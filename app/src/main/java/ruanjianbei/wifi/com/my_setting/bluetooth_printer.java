package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ruanjianbei.wifi.com.Bluetooth_printer.BluetoothActivity;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class bluetooth_printer extends Activity{
    private TextView mReceiver;
    private String content = "蓝牙打印机";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        mReceiver = (TextView) findViewById(R.id.mTextView);
        mReceiver.setText(content);
        Intent intent = new Intent(bluetooth_printer.this, BluetoothActivity.class);
        startActivity(intent);
        bluetooth_printer.this.finish();
    }
}


