package ruanjianbei.wifi.com.ScanActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;

/**
 * 扫描结果Activity
 *
 * @author MrRen
 *
 */
public class ShowActivity extends Activity {
	private TextView txt1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_scan);
		initView();
		initIntent();
	}

	private void initView() {
		txt1 = (TextView) findViewById(R.id.txt1);
	}

	private void initIntent() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			txt1.setText(bundle.getString("msg"));
		}
	}
}
