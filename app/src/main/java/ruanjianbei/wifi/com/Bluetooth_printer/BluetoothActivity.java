package ruanjianbei.wifi.com.Bluetooth_printer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import ruanjianbei.wifi.com.Bluetooth_printer.util.BluetoothAction;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class BluetoothActivity extends Activity {
	private Context context = null;
	private TitleBarView titleBarView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this;
		setContentView(R.layout.bluetooth_layout);
		inittitle();
		this.initListener();
	}

	private void inittitle() {
		titleBarView = (TitleBarView) findViewById(R.id.title_bar);
		titleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		titleBarView.setTitleText(R.string.bluetooth);
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

		bluetoothAction.setSearchDevices(searchDevices);
		bluetoothAction.initView();

		switchBT.setOnClickListener(bluetoothAction);
		searchDevices.setOnClickListener(bluetoothAction);
	}
}
