package ruanjianbei.wifi.com.my_setting.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

/**
 * Created by linankun1 on 2016/5/2.
 */
public class diolog {
    private String str = null;
    public String getcontent(Context context){
        final EditText name=new EditText(context);
         new AlertDialog.Builder(context).setTitle("请输入").setIcon(
                android.R.drawable.ic_dialog_info).setView(name).
                 setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                            str = name.getText().toString();
                     }
                 }).setNegativeButton("取消", null).show();
        return str;
    }
}
