package ruanjianbei.wifi.com.shanchuang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import view.TitleBarView;

public class UserGame extends Activity {
    private TitleBarView titleBarView;
    private int mScreenHeight;
    private int mScreenWidth;
    private int i = 0; // 记录其打到了几只地鼠
    private int a = 0;// 记录一局的回合时间
    private ImageView mouse; // 声明一个ImageView对象
    private Handler handler; // 声明一个Handler对象
    private Timer timer;  //声明一个Timer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_game);
        getWindowManagerSize();
        inittitle();
        timer = new Timer();
        mouse = (ImageView) findViewById(R.id.imageView1); // 获取ImageView对象
        mouse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setVisibility(View.INVISIBLE); // 设置地鼠不显示
                i++;
                return false;
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int index = 0;
                if (msg.what == 0x101) {
                    if(msg.arg1<60){
                        index = msg.arg1; // 获取位置索引值
                        mouse.setX((int) (Math.random() * (mScreenWidth - 40))); // 设置X轴位置
                        mouse.setY((int)(Math.random() *  (mScreenHeight-20))); // 设置Y轴位置
                        mouse.setVisibility(View.VISIBLE); // 设置地鼠显示
                    }else{
                        Toast.makeText(UserGame.this, "一共抓了这么多只" + i, Toast.LENGTH_SHORT).show();
                        timer.cancel();
                        startActivity(new Intent(UserGame.this,GameFriend.class));
                    }
                }
                super.handleMessage(msg);
            }
        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO 自动生成的方法存根
                Message m = new Message(); // 获取一个Message
                m.what = 0x101; // 设置消息标识
                a += 1;
                m.arg1 = a;
                handler.sendMessage(m); // 发送消息
            }
        }, 1000, 500);
    }

    private void getWindowManagerSize() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScreenWidth = outMetrics.widthPixels;
    }

    private void inittitle() {
        titleBarView = (TitleBarView) findViewById(R.id.title_bar);
        titleBarView.setCommonTitle(View.GONE,View.VISIBLE,View.GONE,View.GONE);
        titleBarView.setTitleText(R.string.playnow);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (KeyEvent.ACTION_DOWN == event){
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
