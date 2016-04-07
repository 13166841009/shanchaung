package ruanjianbei.wifi.com.shanchuang;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class UserGame extends Activity {
    private int i = 0; // 记录其打到了几只地鼠
    private int a = 0;// 记录一局的回合时间
    private ImageView mouse; // 声明一个ImageView对象
    private Handler handler; // 声明一个Handler对象
    private Timer timer;  //声明一个Timer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_game);
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
                        mouse.setX((int)(Math.random() *1000)); // 设置X轴位置
                        mouse.setY((int)(Math.random() *1300)); // 设置Y轴位置
                        mouse.setVisibility(View.VISIBLE); // 设置地鼠显示
                    }else{
                        Toast.makeText(UserGame.this, "一共抓了这么多只" + i, Toast.LENGTH_SHORT).show();
                        timer.cancel();
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
                a+=1;
                m.arg1 = a;
                handler.sendMessage(m); // 发送消息
            }
        },1000,500);
    }

}
