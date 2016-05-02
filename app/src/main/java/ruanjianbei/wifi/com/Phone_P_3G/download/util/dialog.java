package ruanjianbei.wifi.com.Phone_P_3G.download.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import ruanjianbei.wifi.com.Phone_P_3G.download.downloadActivity;
import ruanjianbei.wifi.com.Recevie_PageActivity.RecevieMain.ReceiveActivity;

/**
 * Created by linankun1 on 2016/5/2.
 */
public class dialog {
    public int checkDialog(int count, final Context context){
        if(count==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("你没有文件需要接收");
            builder.setTitle("点击确认返回");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, ReceiveActivity.class);
                    context.startActivity(intent);

                }
            });
            builder.create().show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("检测到你有"+count+"个文件需要接收，点击是否接收？");
            builder.setTitle("提示");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(context, ReceiveActivity.class);
                    context.startActivity(intent);
                }
            });
            builder.create().show();
            return 1;
        }
        return 0;
    }
}
