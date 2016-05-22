package ruanjianbei.wifi.com.Bluetooth_printer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import ruanjianbei.wifi.com.Bluetooth_printer.util.BluetoothAction;
import ruanjianbei.wifi.com.shanchuang.R;

public class BluetoothActivity extends Activity {
	private Context context = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		setTitle("蓝牙打印");
		setContentView(R.layout.bluetooth_layout);
		this.initListener();
	}

	private void initListener() {
		/*** 未配对的列表 **/
		ListView unbondDevices = (ListView) this
				.findViewById(R.id.unbondDevices);
		/*** 已经配对的列表 **/
		ListView bondDevices = (ListView) this.findViewById(R.id.bondDevices);
		/**** 蓝牙开关 ***/
		Button switchBT = (Button) this.findViewById(R.id.openBluetooth_tb);
		/**** 搜索蓝牙设备的按钮***/
		Button searchDevices = (Button) this.findViewById(R.id.searchDevices);

		BluetoothAction bluetoothAction = new BluetoothAction(this.context,
				unbondDevices, bondDevices, switchBT, searchDevices,
				BluetoothActivity.this);

		Button returnButton = (Button) this
				.findViewById(R.id.return_Bluetooth_btn);
		bluetoothAction.setSearchDevices(searchDevices);
		bluetoothAction.initView();

		switchBT.setOnClickListener(bluetoothAction);
		searchDevices.setOnClickListener(bluetoothAction);
		returnButton.setOnClickListener(bluetoothAction);
	}
}
