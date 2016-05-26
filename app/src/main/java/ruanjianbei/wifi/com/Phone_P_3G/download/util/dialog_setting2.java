package ruanjianbei.wifi.com.Phone_P_3G.download.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;


public class dialog_setting2 extends Dialog implements
        View.OnClickListener {
    private String dlgContent;
    private int	layoutRes;
    private Context context;
    private TextView dlgbackTV;
    private TextView    dlgPosTV;
    private TextView tv1;
    private OnCustomClickListener onOkClickListener;
    private OnCustomClickListener onCancelClickListener;


    public static interface OnCustomClickListener {
        public void onClick(dialog_setting2 dialog,String str);
//        public void getDialogValue(String str);//回调函数
    }

    /** 离线消息按钮 **/
    public dialog_setting2(Context context) {
        super(context);
        this.context = context;
    }

    public dialog_setting2(Context context, int resLayout) {
        super(context);
        this.context = context;
        layoutRes = resLayout;
    }

    public dialog_setting2(String dlgContent, Context context, int theme,
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
        dlgbackTV = (TextView) findViewById(R.id.dlgbackTV);
        dlgbackTV.setOnClickListener(this);
        tv1.setText(dlgContent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlgbackTV:
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick(this,null);
                }
                dismiss();
                break;
            case R.id.dlgPosTV:
                if (onOkClickListener != null) {
                    onOkClickListener.onClick(this,null);
                }
                break;
            default:
                break;
        }
    }

    public dialog_setting2 setOnOkClickListener(OnCustomClickListener onOkListener) {
        this.onOkClickListener = onOkListener;
        return this;
    }

    public dialog_setting2 setOnCancleClickListener(OnCustomClickListener onCancleListener) {
        this.onCancelClickListener = onCancleListener;
        return this;
    }
}

