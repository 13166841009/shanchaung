package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ruanjianbei.wifi.com.Utils.CircleCurculate.CircleChartView;
import ruanjianbei.wifi.com.my_setting.my_information;
import ruanjianbei.wifi.com.my_setting.tran_history.tran_history;
import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class UserInfoActivity extends Activity {
	private static final String load = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/shangchuan/data/image/face.png";
	private Context mContext;
	private TitleBarView mTitleBarView;
	private ruanjianbei.wifi.com.my_setting.util.DBServiceOperate db;
	private TextView tv_tran;
	private int countU=0,countD=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_mine);
		mContext=this;
		db = new ruanjianbei.wifi.com.my_setting.util.DBServiceOperate(mContext);
		findView();
		init();
		initCircle();
		mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.mime);
		mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
		mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UserInfoActivity.this.finish();
			}
		});
	}

	private void initCircle() {
		CircleChartView circleChartView = (CircleChartView) findViewById(R.id.circle_chart);
		List<CircleChartView.PieceDataHolder> pieceDataHolders = new ArrayList<>();

		pieceDataHolders.add(new CircleChartView.PieceDataHolder(countU*100,0xFFCC9933, "已传输，"+countU));
		pieceDataHolders.add(new CircleChartView.PieceDataHolder(countD*100, 0xFF499BF7, "已接收，"+countD));
		circleChartView.setData(pieceDataHolders);
	}


	private void findView(){
		tv_tran = (TextView) findViewById(R.id.tv_tran);
	}

	private void init(){
		get_your_infor();
	}
	public void get_your_infor(){
		//查看文件传输记录
		ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate db = new
				ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate(mContext);
		Cursor cursor1 = db.selectInformation();
		if(cursor1 != null&&cursor1.getCount()!=0){
			for(cursor1.moveToFirst();!cursor1.isAfterLast();cursor1.moveToNext()){
				String type = cursor1.getString(cursor1.getColumnIndex("type"));
				if(type.equals("上传")){
					countU++;
				}
				if(type.equals("下载")){
					countD++;
				}
			}
			tv_tran.setText("您有"+cursor1.getCount()+"条传输记录，点击查看");
		}else{
			tv_tran.setText("您没有传输记录");
		}
		cursor1.close();
	}
	public void tran_history(View view){
		Intent intent = new Intent(mContext,tran_history.class);
		startActivity(intent);
	}
	public void my_information(View view){
		Intent intent = new Intent(mContext,my_information.class);
		startActivity(intent);
	}
}
