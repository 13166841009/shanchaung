package ruanjianbei.wifi.com.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ruanjianbei.wifi.com.shanchuang.R;


public class CustomDialog extends Dialog implements
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
        public void onClick(CustomDialog dialog);
    }

    /** 离线消息按钮 **/
    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomDialog(Context context, int resLayout) {
        super(context);
        this.context = context;
        layoutRes = resLayout;
    }

    public CustomDialog(String dlgContent, Context context, int theme,
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
//        edtInfoName = (EditText) findViewById(R.id.new_info);
    }

    public String getNewInfo() {
        return edtInfoName.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlgbackTV:
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick(this);
                }
                dismiss();
                break;
            case R.id.dlgPosTV:
                if (onOkClickListener != null) {
                    onOkClickListener.onClick(this);
                }
                break;
            default:
                break;
        }
    }

    public CustomDialog setOnOkClickListener(OnCustomClickListener onOkListener) {
        this.onOkClickListener = onOkListener;
        return this;
    }

    public CustomDialog setOnCancleClickListener(OnCustomClickListener onCancleListener) {
        this.onCancelClickListener = onCancleListener;
        return this;
    }
}