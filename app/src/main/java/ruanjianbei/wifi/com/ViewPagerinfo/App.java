package ruanjianbei.wifi.com.ViewPagerinfo;


import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by 郭攀峰 on 2015/9/11.
 */
public class App extends Application
{

    private static App instance;

    public static int SCREEN_WIDTH;


    @Override
    public void onCreate()
    {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        instance = this;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        SCREEN_WIDTH = Math.min(screen.x, screen.y);

        initImageLoader();
    }

    private void initImageLoader()
    {
//        ImageLoaderConfig config = new ImageLoaderConfig()
//                .setLoadingPlaceholder(R.drawable.icon_loading)
//                .setCache(new MemoryCache())
//                .setLoadPolicy(new SerialPolicy());
//        SimpleImageLoader.getInstance().init(config);
    }

    public static App getInstance()
    {
        return instance;
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
    }
}
