package ruanjianbei.wifi.com.my_setting.tran_history;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ruanjianbei.wifi.com.Phone_P_3G.util.DBServiceOperate;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class tran_history extends Activity{
    private ListView mList;
    private ListAdapter mListAdapter = null;
    private List<String[]> file_info;
    private DBServiceOperate db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_list);
        mList = (ListView) findViewById(R.id.lv);
        db = new DBServiceOperate(tran_history.this);
        file_info = new ArrayList<String[]>();
        readSQLdata();
    }
    public void readSQLdata(){
        Cursor cursor = db.selectInformation();
        if (cursor != null) {
/*            if (cursor.moveToFirst()) {
                for(int i=0;i<cursor.getCount();i++){
                    cursor.move(i);//移动到指定记录
                    String file_name = cursor.getString(cursor.getColumnIndex("file_name"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String str[] = {file_name,time,type};
                    file_info.add(str);
                }
                此方法有bug，不能用，数组会越位
            }*/
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                String file_name = cursor.getString(cursor.getColumnIndex("file_name"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String str[] = {file_name,time,type};
                file_info.add(str);
            }
            Toast.makeText(tran_history.this, "有内容+"+cursor.getCount(), Toast.LENGTH_SHORT).show();
            //添加适配器
            mListAdapter = new ListAdapter(tran_history.this,file_info);
            mList.setAdapter(mListAdapter);
        }else{
            Toast.makeText(tran_history.this, "没有内容", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}


