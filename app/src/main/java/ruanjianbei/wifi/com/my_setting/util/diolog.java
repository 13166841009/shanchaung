package ruanjianbei.wifi.com.my_setting.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/2.
 */
public class diolog {
    public String str = null;
/*    public void getcontent(final Context context, final TextView et, final String lx,
                           final DBServiceOperate db){
        final EditText name=new EditText(context);
         new AlertDialog.Builder(context).setTitle("请输入").setIcon(
                android.R.drawable.ic_dialog_info).setView(name).
                 setPositiveButton("确定", new android.content.DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                            str = name.getText().toString();
                            et.setText(str);
                            db.upDateInformation(lx, str);
                     }
                 }).setNegativeButton("取消", null).show();
    }*/
    public void getSEX(final Context context,final TextView et,final String lx,
                       final DBServiceOperate db){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("请选择性别");
        final String[] sex = {"男", "女"};
        builder.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
//                Toast.makeText(my_information.this, "性别为：" + sex[which], Toast.LENGTH_SHORT).show();
                str = sex[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                et.setText(str);
                DBServiceOperate db = new DBServiceOperate(context);
                db.upDateInformation(lx,str);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void getContent(final Context context,final TextView et,final String lx,
                       final DBServiceOperate db){
        Dialog_setting dialog = new Dialog_setting("请输入",context,R.style.dialogstyle,
                R.layout.activity_linankun_dialog);
        dialog.setOnOkClickListener(new Dialog_setting.OnCustomClickListener() {
            @Override
            public void onClick(Dialog_setting dialog, String str1) {
                et.setText(str1);
                db.upDateInformation(lx, str1);
                dialog.dismiss();
            }
        });
        dialog.setOnCancleClickListener(new Dialog_setting.OnCustomClickListener() {

            @Override
            public void onClick(Dialog_setting dialog, String str) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
