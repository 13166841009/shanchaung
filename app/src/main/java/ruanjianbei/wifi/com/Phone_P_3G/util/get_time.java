package ruanjianbei.wifi.com.Phone_P_3G.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linankun1 on 2016/5/6.
 */
public class get_time {
    public String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return  str;
    }
}
