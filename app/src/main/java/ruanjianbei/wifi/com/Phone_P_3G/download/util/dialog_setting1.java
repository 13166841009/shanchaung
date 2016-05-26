package ruanjianbei.wifi.com.Phone_P_3G.download.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;



public class dialog_setting1 extends Dialog implements
        View.OnClickListener {
    private String dlgContent;
    private int	layoutRes;
    private Context context;
    private TextView dlgPosTV;
    private TextView tv1;
    private OnCustomClickListener onCancelClickListener;


    public static interface OnCustomClickListener {
        public void onClick(dialog_setting1 dialog, String str);
//        public void getDialogValue(String str);//回调函数
    }

    /** 离线消息按钮 **/
    public dialog_setting1(Context context) {
        super(context);
        this.context = context;
    }

    public dialog_setting1(Context context, int resLayout) {
        super(context);
        this.context = context;
        layoutRes = resLayout;
    }

    public dialog_setting1(String dlgContent, Context context, int theme,
                           int resLayout) {
        super(context, theme);
        this.context = context;
        layoutRes = resLayout;
        this.dlgContent = dlgContent;
    }

/*    public CustomDialog(String dlgContent, Context context, int theme,
                        int resLayout, OnCustomClickListener listener) {
        super(context, theme);
        this.context = context;
        layoutRes = resLayout;
        this.dlgContent = dlgContent;
        this.listener = listener;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(layoutRes);
        tv1 = (TextView) findViewById(R.id.tv1);
        dlgPosTV = (TextView) findViewById(R.id.dlgPosTV);
        dlgPosTV.setOnClickListener(this);
        tv1.setText(dlgContent);
    }

    @Override
    public void onClick(View v) {
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick(this,null);
                }
                dismiss();
    }
    public dialog_setting1 setOnCancleClickListener(OnCustomClickListener onCancleListener) {
        this.onCancelClickListener = onCancleListener;
        return this;
    }
}

