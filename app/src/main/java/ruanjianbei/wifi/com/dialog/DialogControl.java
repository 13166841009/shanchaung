package ruanjianbei.wifi.com.dialog;

import android.app.ProgressDialog;

/**
 * Created by Administrator on 2016/1/20 0020.
 */
public interface DialogControl {
    public abstract void hideWaitDialog();
    public abstract ProgressDialog showWaitDialog();
    public abstract  ProgressDialog showWaitDialog(int resId);
    public abstract ProgressDialog showWaitDialog(String text);

}
