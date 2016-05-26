package ruanjianbei.wifi.com.Phone_P_3G.download.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/2.
 */
public class dialog {
    public int checkDialog(int count, final Context context){
        if(count==0){
                final dialog_setting1 dialog1=new dialog_setting1("你没有文件需要接收",context, R.style.dialogstyle,
                        R.layout.activity_download_dialog1);
                dialog1.setOnCancleClickListener(new dialog_setting1.OnCustomClickListener() {
                    @Override
                    public void onClick(dialog_setting1 dialog, String str) {
                        dialog1.dismiss();
                    }
                });
            dialog1.show();
        }
        else {
                 final dialog_setting2 dialog2=new dialog_setting2("你有"+count+"个文件接收，点击确定接收",context,R.style.dialogstyle,
                        R.layout.activity_download_dialog2);
                dialog2.setOnOkClickListener(new dialog_setting2.OnCustomClickListener() {
                    @Override
                    public void onClick(dialog_setting2 dialog, String str) {
                        dialog2.dismiss();
                    }
                });
                dialog2.setOnCancleClickListener(new dialog_setting2.OnCustomClickListener() {
                    @Override
                    public void onClick(dialog_setting2 dialog, String str) {
                        Intent intent = new Intent(context,ReceiveActivity.class);
                        context.startActivity(intent);
                        dialog2.dismiss();
                    }
                });
            dialog2.show();
            return 1;
        }
        return 0;
    }
}
