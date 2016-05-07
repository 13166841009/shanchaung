package ruanjianbei.wifi.com.my_setting.util;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;


public class Dialog_setting extends Dialog implements
        View.OnClickListener {
    private String dlgContent;
    private int	layoutRes;
    private Context context;
    private TextView			dlgbackTV;
    private TextView			dlgPosTV;
    private EditText edtInfoName;
    private OnCustomClickListener onOkClickListener;
    private OnCustomClickListener onCancelClickListener;


    public static interface OnCustomClickListener {
        public void onClick(Dialog_setting dialog,String str);
//        public void getDialogValue(String str);//回调函数
    }

    /** 离线消息按钮 **/
    public Dialog_setting(Context context) {
        super(context);
        this.context = context;
    }

    public Dialog_setting(Context context, int resLayout) {
        super(context);
        this.context = context;
        layoutRes = resLayout;
    }

    public Dialog_setting(String dlgContent, Context context, int theme,
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
        dlgPosTV = (TextView) findViewById(R.id.dlgPosTV);
        TextView dialog_contentTV = (TextView) findViewById(R.id.dialog_contentTV);
        dialog_contentTV.setText(dlgContent);
        dlgPosTV.setOnClickListener(this);
        dlgbackTV = (TextView) findViewById(R.id.dlgbackTV);
        dlgbackTV.setOnClickListener(this);
        edtInfoName = (EditText) findViewById(R.id.et);
    }

    public String getNewInfo() {
        return edtInfoName.getText().toString();
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
                    onOkClickListener.onClick(this,edtInfoName.getText().toString());
                }
                break;
            default:
                break;
        }
    }

    public Dialog_setting setOnOkClickListener(OnCustomClickListener onOkListener) {
        this.onOkClickListener = onOkListener;
        return this;
    }

    public Dialog_setting setOnCancleClickListener(OnCustomClickListener onCancleListener) {
        this.onCancelClickListener = onCancleListener;
        return this;
    }
}
