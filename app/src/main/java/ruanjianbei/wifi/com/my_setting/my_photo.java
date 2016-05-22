package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class my_photo extends Activity {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="faca8758f0e34ba1a3523b468e929c08";

    private static List<String> typeList = new ArrayList<String>();
    private static List<String> xiaohuaList = new ArrayList<String>();

    private ListView listView;
    private String content = "开心一刻";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_photo);
        listView = (ListView) findViewById(R.id.xiaohua);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        getRequest1();
        listView.setAdapter(new ArrayAdapter<String>(my_photo.this,
                android.R.layout.simple_list_item_1, typeList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getRequest2(position);
                listView.setAdapter(new ArrayAdapter<String>(my_photo.this,
                        android.R.layout.simple_list_item_1, xiaohuaList));
            }
        });
    }

    //1.返回接口类型
    public static void getRequest1(){
        String result =null;
        String url ="http://japi.juhe.cn/funny/type.from";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key", APPKEY);//您申请的key

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                JSONObject jsonObject = (JSONObject) object.get("result");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    for(int j = 1 ; j < jsonObject1.length() ; j++){
                        String name = jsonObject1.getString(""+ j);
                        typeList.add(j+"、"+name);
                    }
                }
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //2.根据接口序号查询
    public static void getRequest2(int position){
        String result =null;
        String url ="http://japi.juhe.cn/funny/list.from";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("cat","");//指定接口类型,默认1
        params.put("st", position);//指定开始数,默认0
        params.put("count", "");//指定返回个数,默认1
        params.put("key",APPKEY);//您申请的key

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                JSONObject jsonObject = (JSONObject) object.get("result");
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0 ; i < jsonArray.length() ; i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    for(int j = 1 ; j < jsonObject1.length() ; j++){
                        String name = jsonObject1.getString(""+ j);
                        xiaohuaList.add(j+"、"+name);
                    }
                }
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.按类搜索接口
    public static void getRequest3(){
        String result =null;
        String url ="http://japi.juhe.cn/funny/search.from";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("st","");//指定开始数,默认0
        params.put("count","");//指定返回数量,默认1
        params.put("term","impoverished");//指定搜索关键词
        params.put("key",APPKEY);//您申请的key

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //4.参考答案接口
    public static void getRequest4(){
        String result =null;
        String url ="http://japi.juhe.cn/funny/answer.from";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("id","");//指定谜语、歇后语、打油诗、脑筋急转弯编号

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
