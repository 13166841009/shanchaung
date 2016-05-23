package ruanjianbei.wifi.com.Bluetooth_printer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ruanjianbei.wifi.com.Bluetooth_printer.util.PrintDataAction;
import ruanjianbei.wifi.com.Bluetooth_printer.util.PrintDataService;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class PrintDataActivity extends Activity {  
    private Context context = null;  
    private TitleBarView titleBarView;
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.printdata_layout);
        inittitle();
        this.context = this;  
        this.initListener();  
    }

    private void inittitle() {
        titleBarView = (TitleBarView) findViewById(R.id.title_bar);
        titleBarView.setTitleText(R.string.printinfo);
        titleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        titleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        titleBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintDataActivity.this.finish();
            }
        });
    }

    /** 
     * 获得从上一个Activity传来的蓝牙地址
     * @return String 
     */  
    private String getDeviceAddress() {  
        // 直接通过Context类的getIntent()即可获取Intent
        Intent intent = this.getIntent();  
        // 判断
        if (intent != null) {  
            return intent.getStringExtra("deviceAddress");  
        } else {  
            return null;  
        }  
    }  
  
    private void initListener() {  
        TextView deviceName = (TextView) this.findViewById(R.id.device_name);  
        TextView connectState = (TextView) this  
                .findViewById(R.id.connect_state);  
  
        PrintDataAction printDataAction = new PrintDataAction(this.context,
                this.getDeviceAddress(), deviceName, connectState);  
  
        EditText printData = (EditText) this.findViewById(R.id.print_data);  
        Button send = (Button) this.findViewById(R.id.send);  
        Button command = (Button) this.findViewById(R.id.command);  
        printDataAction.setPrintData(printData);  
  
        send.setOnClickListener(printDataAction);  
        command.setOnClickListener(printDataAction);  
    }  
  
      
    @Override  
    protected void onDestroy() {  
        PrintDataService.disconnect();
        super.onDestroy();  
    }  
      
}
