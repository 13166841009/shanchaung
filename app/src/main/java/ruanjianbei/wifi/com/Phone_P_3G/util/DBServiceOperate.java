package ruanjianbei.wifi.com.Phone_P_3G.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by linankun1 on 2016/5/6.
 */
public class DBServiceOperate {
    private Context context;
    private DBService operate;
    private SQLiteDatabase database;
    public DBServiceOperate(Context context){
        this.context = context;
        this.operate  = new DBService(context);
    }
    public void saveInformation(String file_name,String time,String type){
        //如果数据库不存在，则创建一个数据库，再可读可写数据库对象；如果数据库存在，则直接打开
        database = operate.getWritableDatabase();
        database.execSQL("insert into file_info(file_name,time,type)values(?,?,?)",new Object[]{file_name,time,type});
        database.close();
    }
    //查询的结果集
    public Cursor selectInformation(){
        database = operate.getWritableDatabase();
        return database.rawQuery("select * from file_info", null);
    }
}
