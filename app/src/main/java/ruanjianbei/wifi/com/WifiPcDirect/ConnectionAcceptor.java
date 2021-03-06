package ruanjianbei.wifi.com.WifiPcDirect;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import ruanjianbei.wifi.com.ScanActivity.camera.Utils;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by zhanghang on 4/17/2016.
 */
public class ConnectionAcceptor implements Runnable {
    private WifiPcActivity mainActivity;
    boolean stop = false;
    int port = 0;
    ServerSocket ssock;
    HashSet<SingleConnection> hsT; // connection list
    HashSet<InetAddress> hs; // unique ip list
    HashSet<InetAddress> hsP; // authorized users list


    ConnectionAcceptor(WifiPcActivity mainActivity){
        this.mainActivity = mainActivity;

    }

    /**
     * Authorizes a connection and adds it to the list
     * @param conn Connection attempting to authorize
     * @param password Password attempted by connection
     * @return True if correct password
     */
    synchronized boolean authUser(SingleConnection conn, String password)
    {
//        if(password == mainActivity.)
//        System.out.println("pass: " + password + " attempted");
//        System.out.println("pass=" + mainActivity.getPassword());
        if(password.equals(mainActivity.getPassword())) {
            if(!hsP.contains(conn.sock.getInetAddress()))
                hsP.add(conn.sock.getInetAddress());
            return true;
        }
        else
            return false;
    }
    synchronized boolean getAuth(InetAddress addr)
    {
        return hsP.contains(addr);
    }
    synchronized void removeAllAuth()
    {
        hsP.clear();
    }

    synchronized void removeConn(SingleConnection conn)
    {
        if(hsT.contains(conn))
            hsT.remove(conn);
    }
    synchronized void addConn(SingleConnection conn)
    {
        hsT.add(conn);
    }


    public void stop() {
        stop = true;
    }

    @Override
    public void run() {
        try {
            ssock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }



        WifiManager wm = (WifiManager) mainActivity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wi = wm.getConnectionInfo();
        int ip = wi.getIpAddress();
        final String ipstr;
//        System.out.println(ip);
        if(ip != 0x00000000)
            ipstr = (ip & 0xff) + "." + ((ip >> 8) & 0xff) + "." + ((ip >> 16) & 0xff)
                    + "." + ((ip >> 24) & 0xff) + ":" + ssock.getLocalPort();
        else
            ipstr = "请连接合适的wifi或热点";


        // changes the ip address shown in the app
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) mainActivity.findViewById(R.id.ip);
                ImageView mImageView = (ImageView) mainActivity.findViewById(R.id.qrcode_bitmap);
                tv.setText("IP："+ipstr);
                DisplayMetrics metric = new DisplayMetrics();
                mainActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
                int width = metric.widthPixels;
                try {

                    mImageView.setImageBitmap(Utils.createQRCode("http://"+ipstr, (int) (width/1.5)));
                    // Resources res = getResources();
                    // Bitmap bmp = BitmapFactory.decodeResource(res,
                    // R.drawable.ic_xiner);
                    // mImageView.setImageBitmap(Utils.createQRCodeLogo(str, width / 2,
                    // bmp));

                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

//        System.out.println("Set ip: " + ipstr);


        hs = new HashSet<>();

        hsT = new HashSet<>();
        hsP = new HashSet<>();

        while(!stop)
        {
            try {
                Socket s = ssock.accept();
//                System.out.println("sendbuffersize:" + s.getSendBufferSize());
//                System.out.println("recvbuffersize:" + s.getReceiveBufferSize());
                SingleConnection serv = new SingleConnection(mainActivity, this, s, getAuth(s.getInetAddress()));
                addConn(serv);
                new Thread(serv).start();

                if(!hs.contains(s.getInetAddress()))
                {
                    hs.add(s.getInetAddress());
                    mainActivity.makeToast("新的PC端连接: " + s.getInetAddress(), false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ssock.close();
            for(SingleConnection i : hsT)
            {
                i.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("could not close server socket used in acceptor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}