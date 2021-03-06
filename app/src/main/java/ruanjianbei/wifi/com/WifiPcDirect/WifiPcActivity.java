package ruanjianbei.wifi.com.WifiPcDirect;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

import ruanjianbei.wifi.com.shanchuang.R;
import view.TitleBarView;

public class WifiPcActivity extends Activity {
    private TitleBarView mTitleBarView;

    ConnectionAcceptor acceptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_pc_direct);
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        init();
        ((CheckBox)findViewById(R.id.checkbox_download)).setChecked(pref.getBoolean("download", true));
        ((CheckBox)findViewById(R.id.checkbox_upload)).setChecked(pref.getBoolean("upload", true));
        ((CheckBox)findViewById(R.id.checkbox_rename)).setChecked(pref.getBoolean("rename", true));
        ((CheckBox)findViewById(R.id.checkbox_deletion)).setChecked(pref.getBoolean("delete", true));
        ((CheckBox)findViewById(R.id.checkbox_toasts)).setChecked(pref.getBoolean("toasts", true));


        if(makeWifiTransferDir()) { // only if default folder exists.
            acceptor = new ConnectionAcceptor(this);
            new Thread(acceptor).start();
        }
    }

    private void init() {
        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.connectpc);
        mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
        mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiPcActivity.this.finish();
            }
        });
    }

    /**
     * Makes directory for this app
     * @return True if successful or it already exists. False if unsuccessful, external storage
     * cannot be mounted, or it does not exist and could not be made.
     */
    private boolean makeWifiTransferDir() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            System.out.println("Can't open external storage");
            return false;
        }

        File mainFolder = new File(Environment.getExternalStorageDirectory(), "WifiTransfer");
        if(!(mainFolder.exists() && mainFolder.isDirectory())) // if doesn't exist, create it.
            return mainFolder.mkdir(); //  if fail to make, return false
        return true;
    }


    Context getAppContext(){
        return getApplicationContext();
    }

    void makeToast(final String s, final boolean displayForLongTime) {
        final Context context = getApplicationContext();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CheckBox canToast = (CheckBox) findViewById(R.id.checkbox_toasts);
                if (!canToast.isChecked())
                    return;
                Toast toast;
                if (displayForLongTime)
                    toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
                else
                    toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        outState.putBoolean("download", ((CheckBox)findViewById(R.id.checkbox_download)).isChecked());
        outState.putBoolean("upload", ((CheckBox)findViewById(R.id.checkbox_upload)).isChecked());
        outState.putBoolean("rename", ((CheckBox)findViewById(R.id.checkbox_rename)).isChecked());
        outState.putBoolean("delete", ((CheckBox)findViewById(R.id.checkbox_deletion)).isChecked());
        outState.putBoolean("toasts", ((CheckBox)findViewById(R.id.checkbox_toasts)).isChecked());
        super.onSaveInstanceState(outState);
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("download", ((CheckBox)findViewById(R.id.checkbox_download)).isChecked());
        editor.putBoolean("upload", ((CheckBox)findViewById(R.id.checkbox_upload)).isChecked());
        editor.putBoolean("rename", ((CheckBox)findViewById(R.id.checkbox_rename)).isChecked());
        editor.putBoolean("delete", ((CheckBox)findViewById(R.id.checkbox_deletion)).isChecked());
        editor.putBoolean("toasts", ((CheckBox)findViewById(R.id.checkbox_toasts)).isChecked());
        editor.commit();

    }
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        acceptor.stop();
    }

    String getPassword(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("password_value", "not found");
    }

    boolean isPasswordRequired(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("password_active_checkbox", false);
    }

    void checkPassword(){
        String x = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("password_value", "");
        System.out.println("pass=" + x);
    }

    void showPreferenceList(){
        for (Map.Entry x : PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getAll().entrySet())
            System.out.println(x.getKey() + ":" + x.getValue());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
