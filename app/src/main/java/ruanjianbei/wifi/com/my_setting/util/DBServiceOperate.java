package ruanjianbei.wifi.com.my_setting.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by linankun1 on 2016/5/4.
 */
public class DBServiceOperate {
    private  Context context;
    private DBService operate;
    private SQLiteDatabase database;
    public DBServiceOperate(Context context){
        this.context = context;
        this.operate  = new DBService(context);
    }
    public void saveInformation(Bitmap bitmap,String name,String sex,
                                   String number,String email,String address){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        //如果数据库不存在，则创建一个数据库，再可读可写数据库对象；如果数据库存在，则直接打开
        database = operate.getWritableDatabase();
        database.execSQL("insert into information(Photo,Name,Sex,Number,Email,Address" +
                ")values(?,?,?,?,?,?)",new Object[]{baos.toByteArray(),name,sex,number,email,address});
        database.close();
    }
    public void upDateInformation(Bitmap bitmap){
        database = operate.getWritableDatabase();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        database.execSQL("update information set Photo = ? where _id = ?",
                new Object[]{baos.toByteArray(), 1});
        database.close();
    }
    public void upDateInformation(String leixing,String data){
        switch (leixing) {
            case "Name":
                database = operate.getWritableDatabase();
                database.execSQL("update information set Name = ? where _id = ?",
                        new Object[]{data,1});
                break;
            case "Sex":
                database = operate.getWritableDatabase();
                database.execSQL("update information set Sex = ? where _id = ?",
                        new Object[]{data,1});
                break;
            case "Number":
                database = operate.getWritableDatabase();
                database.execSQL("update information set Number = ? where _id = ?",
                        new Object[]{data,1});
                break;
            case "Email":
                database = operate.getWritableDatabase();
                database.execSQL("update information set Email = ? where _id = ?",
                        new Object[]{data,1});
                break;
            case "Address":
                database = operate.getWritableDatabase();
                database.execSQL("update information set Address = ? where _id = ?",
                        new Object[]{data,1});
                break;
        }
        database.close();
    }
    //查询的结果集
    public Cursor selectInformation(){
        database = operate.getWritableDatabase();
        Cursor cs = database.rawQuery("select * from information where _id = 1", new String[]{});
//        database.close();
        return cs;
    }
    public void Close(){
        database.close();
    }
}
