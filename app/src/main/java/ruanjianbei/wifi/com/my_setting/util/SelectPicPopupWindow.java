package ruanjianbei.wifi.com.my_setting.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import ruanjianbei.wifi.com.my_setting.HappyTime;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by zhouyonglong on 2016/5/23.
 */
public class SelectPicPopupWindow extends PopupWindow {
    private TextView tv_content;
    private Button btn_cancel;
    private View mMenuView;
    public SelectPicPopupWindow(Activity context,OnClickListener itemsOnClick){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.alert_dialog, null);
        tv_content = (TextView) mMenuView.findViewById(R.id.tv_content);
//        btn_pick_ans = (Button) mMenuView.findViewById(R.id.btn_pick_ans);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);

        tv_content.setText(HappyTime.xiaohua_content);
        //取消按钮
        btn_cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.anim.animbottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.popWind).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
