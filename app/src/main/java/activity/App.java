package activity;

import android.app.Activity;
import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by linankun1 on 2016/5/15.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

}
