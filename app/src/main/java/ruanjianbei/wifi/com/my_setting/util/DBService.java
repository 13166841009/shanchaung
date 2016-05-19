package ruanjianbei.wifi.com.my_setting.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

/**
 * Created by linankun1 on 2016/5/4.
 */
public class DBService extends SQLiteOpenHelper {

    private final static int VERSION = 1;
    private final static String DATABASE_NAME = "my_information.db";
    public DBService(Context context) {
        this(context, DATABASE_NAME, null, VERSION);
    }
    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table information(_id integer primary key autoincrement," +
                "Photo binary,Name char(15),Sex char(5)," +
                "Number char(20),Email char(30),Address char(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS information");
        } else {
            return;
        }
        onCreate(db);
    }
}
