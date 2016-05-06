package ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;

import com.bruce.library.ComboView;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.Android_receiveActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.RecevieByWifi;
import ruanjianbei.wifi.com.shanchuang.R;

public class ReceiveActivity extends Activity {
    /**
     * 进入后进行文件传输存在bug
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        ComboView android = (ComboView) findViewById(R.id.android_receive);
        ComboView ios = (ComboView) findViewById(R.id.ios_receive);
        ComboView.Params params = ComboView.Params.create()

                //Android文件传输
                .cornerRadius(dimen(R.dimen.cb_dimen_25), dimen(R.dimen.cb_dimen_52))// Following three to***** values must be the same can morph to circle
                .width(dimen(R.dimen.cb_dimen_70), dimen(R.dimen.cb_dimen_52))
                .height(dimen(R.dimen.cb_dimen_38), dimen(R.dimen.cb_dimen_52))
                .morphDuration(300)
                .text("ANDROID文件接收", "点击进入")
                        //Option -- and values below is default
                .color(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .colorPressed(color(R.color.cb_color_blue_dark), color(R.color.cb_color_blue_dark))
                .strokeWidth(dimen(R.dimen.cb_dimen_1), dimen(R.dimen.cb_dimen_1))
                .strokeColor(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .circleDuration(3000)
                .rippleDuration(200)
                .padding(dimen(R.dimen.cb_dimen_3))
                .textSize(16)
                .textColor(color(R.color.cb_color_white))
                .comboClickListener(new ComboView.ComboClickListener() {
                    @Override
                    public void onComboClick() {
                        /**
                         * 对当前网络添加判断
                         */
                        startActivity(new Intent(ReceiveActivity.this, Android_receiveActivity.class));
                    }

                    @Override
                    public void onNormalClick() {

                    }
                });
        ComboView.Params iosparams = ComboView.Params.create()

                //Android文件传输
                .cornerRadius(dimen(R.dimen.cb_dimen_25), dimen(R.dimen.cb_dimen_52))// Following three to***** values must be the same can morph to circle
                .width(dimen(R.dimen.cb_dimen_70), dimen(R.dimen.cb_dimen_52))
                .height(dimen(R.dimen.cb_dimen_38), dimen(R.dimen.cb_dimen_52))
                .morphDuration(300)
                .text("IOS文件接收", "点击进入")
                        //Option -- and values below is default
                .color(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .colorPressed(color(R.color.cb_color_blue_dark), color(R.color.cb_color_blue_dark))
                .strokeWidth(dimen(R.dimen.cb_dimen_1), dimen(R.dimen.cb_dimen_1))
                .strokeColor(color(R.color.cb_color_blue), color(R.color.cb_color_blue))
                .circleDuration(3000)
                .rippleDuration(200)
                .padding(dimen(R.dimen.cb_dimen_3))
                .textSize(16)
                .textColor(color(R.color.cb_color_white))
                .comboClickListener(new ComboView.ComboClickListener() {
                    @Override
                    public void onComboClick() {
                        startActivity(new Intent(ReceiveActivity.this, downloadActivity.class));
                    }

                    @Override
                    public void onNormalClick() {
                    }
                });
        android.settingMorphParams(params);
        ios.settingMorphParams(iosparams);
    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

