package ruanjianbei.wifi.com.shanchuang;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.WiFiActivity;
import view.TitleBarView;

/**
 * Created by zhouyonglong on 2016/5/21.
 */
public class GameFriend extends Activity {

    private Button begin ;
    private ListView listView ;
    private TitleBarView mTitleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadisu);
        begin = (Button) findViewById(R.id.begin);
        listView = (ListView) findViewById(R.id.friend);
        mTitleBarView=(TitleBarView)findViewById(R.id.title_bar);
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.dds);
        mTitleBarView.setBtnLeft(R.mipmap.boss_unipay_icon_back, R.string.back);
//        mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GameFriend.this.finish();
//            }
//        });

        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.list_dadisu,
                new String[]{"title","info","img"},
                new int[]{R.id.title,R.id.info,R.id.img});
        listView.setAdapter(adapter);

        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameFriend.this,UserGame.class);
                startActivity(intent);
            }
        });
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "好友1");
        map.put("info", "最高分数:99");
        map.put("img", R.drawable.icon_delete);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "好友2");
        map.put("info", "最高分数:98");
        map.put("img", R.drawable.icon_success);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "好友3");
        map.put("info", "最高分数:99");
        map.put("img", R.drawable.icon_success);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "好友4");
        map.put("info", "最高分数:99");
        map.put("img", R.drawable.icon_success);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "好友5");
        map.put("info", "最高分数:99");
        map.put("img", R.drawable.icon_success);
        list.add(map);
        return list;
    }
}


