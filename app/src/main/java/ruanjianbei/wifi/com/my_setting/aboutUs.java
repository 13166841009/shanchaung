package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class aboutUs extends Activity{
    private TextView mReceiver;
    private String content = "关于我们";

    private RelativeLayout weibo ;
    private RelativeLayout weixing;
    private RelativeLayout qq;
    private TitleBarView mTitleBarView;

    private ImageView img_connect_us;
    private boolean flag = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findview();

        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.aboutus);
        mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
    }

    private void findview(){
        mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
        weibo = (RelativeLayout) findViewById(R.id.weibo);
        weixing = (RelativeLayout) findViewById(R.id.weixing);
        qq = (RelativeLayout) findViewById(R.id.qq);
        img_connect_us = (ImageView) findViewById(R.id.img_connect_us);
    }

    public void onclick_gw(View view){
        Toast.makeText(this,"官网正在制作中...",Toast.LENGTH_SHORT).show();
    }

    public void onclick_us(View view){
        if(flag){
            weibo.setVisibility(View.VISIBLE);
            weixing.setVisibility(View.VISIBLE);
            qq.setVisibility(View.VISIBLE);
            img_connect_us.setImageResource(R.mipmap.qvip_pay_wallet_icon_arrow_right_normal);
            flag = false;
        }else{
            weibo.setVisibility(View.GONE);
            weixing.setVisibility(View.GONE);
            qq.setVisibility(View.GONE);
            img_connect_us.setImageResource(R.mipmap.qvip_pay_wallet_icon_arrow_right_normal);
            flag = true;
        }
    }

    public void onclick_updata(View view){
        Toast.makeText(this,"已是最新版本！",Toast.LENGTH_SHORT).show();
    }
}
