package ruanjianbei.wifi.com.Phone_P_3G.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by linankun1 on 2016/5/4.
 */
public class DBService extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DATABASE_NAME = "file.db";
    public DBService(Context context) {
        this(context, DATABASE_NAME, null, VERSION);
    }
    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table file_info(_id integer primary key autoincrement," +
                "file_name char(50),time char(20),type char(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS file_info");
        } else {
            return;
        }
        onCreate(db);
    }
}