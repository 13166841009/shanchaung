package ruanjianbei.wifi.com.Recevie_PageActivity.RecevieWifi.utils;

/**
 * Created by dell-pc on 2016/5/5.
 */
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class Wifistatus {

    private Handler handler;
    private String checkUrl;
    private int checkTimeout;

    public Wifistatus(
            final String checkUrl , final int checkTimeout ,
            Handler handler) {

        this.handler = handler;
        this.checkUrl = checkUrl;
        this.checkTimeout = checkTimeout;

        startCheck();
    }

    private void startCheck() {
        final long webServerCheckStartTime = System.currentTimeMillis();
        final int webServerTimeOutInt = 500;  //网络状态检测超时时长，单位为毫秒


        //使用新线程检测网络状态
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();

                //System.out.println("开始检测网络状态.......actionFlag=" + wifiDetection.getActionFlag());

                new Thread(new Runnable() {
                    public void run() {
                        Looper.prepare();
                        if (checkT_Network(checkUrl, webServerTimeOutInt)) {
                            sendMsgToCheckWebserverHandler(0);
                        }else{
                            sendMsgToCheckWebserverHandler(1);
                        }
                    }
                }).start();
            }
        }).start();
    }

    private void sendMsgToCheckWebserverHandler(int what) {
        Message msg = new Message();
        msg.what = what;
        handler.sendMessage(msg);
    }


    /**
     * Head请求判断指定网络地址的http服务端口是否可以访问
     *
     * @param serverUrl
     * @return
     */
    private static boolean checkT_Network(String serverUrl, int timerout) {

        boolean newWorkOk_Flag = false;


        int responseCode = 0;

        HttpURLConnection conn = null;
        try {

            URL url = new URL(serverUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Connection", "Close");

            conn.setRequestMethod("HEAD");
            conn.setConnectTimeout(timerout);
            conn.connect();

            responseCode = conn.getResponseCode();

            //返回的状态
            if (responseCode == 200 || responseCode == 206 || responseCode == 404) {

                newWorkOk_Flag = true;

            }
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        } catch (ProtocolException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return newWorkOk_Flag;
    }

}