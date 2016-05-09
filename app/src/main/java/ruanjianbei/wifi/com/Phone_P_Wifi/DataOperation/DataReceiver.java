package ruanjianbei.wifi.com.Phone_P_Wifi.DataOperation;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

import ruanjianbei.wifi.com.Phone_P_Wifi.PostFileAdapter.MediaItem;
import ruanjianbei.wifi.com.Phone_P_Wifi.Socket.SocketServer;
import ruanjianbei.wifi.com.Phone_P_Wifi.Utils.Utils;

/**
 * 数据接收者
 * @author simon.L
 * @version 1.0.0
 */
public class DataReceiver implements SocketServer.SocketListener{

    private static final String LOG_TAG = "DataReceiver";
    private SocketServer mServer;
    private String mStoreDir;
    private DataTransferListener mDataTransferListener;
    private Handler mUiHandler;

    /**
     * 以数据保存目录来构造对象
     * @param storeDir 数据保存目录
     * @param listener 数据传输的回调接口
     */
    public DataReceiver(String storeDir, DataTransferListener listener) {
        mServer = new SocketServer(this);
        mStoreDir = storeDir;
        mDataTransferListener = listener;
        mUiHandler = new Handler(Looper.getMainLooper());

        if (TextUtils.isEmpty(storeDir)) {
            throw new IllegalArgumentException("storeDir must be valid");
        } else {
            File file = new File(storeDir);
            if (!file.mkdirs() && !file.isDirectory()) {
                throw new IllegalArgumentException("storeDir must be valid");
            }
        }
    }

    private MediaItem parseHead(BufferedReader reader, byte[] buffer) throws IOException{
        Properties head = new Properties();
        String line = reader.readLine();
        //System.out.println("文件接收第一行代码"+line);
        while (line != null && line.trim().length() > 0) {
            int p = line.indexOf(':');
            //System.out.println("p的大小"+p+line);
            if (p >= 0) {
                head.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
            }
            line = reader.readLine();
        }

        return new MediaItem(head);
    }

    @Override
    public void onReceive(InputStream inputStream) {
        byte[] buffer = new byte[Utils.RECEIVE_BUFFER_SIZE];
        try {
            int len = inputStream.read(buffer, 0, Utils.RECEIVE_BUFFER_SIZE);
            if (len <= 0) {
                return;
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer, 0, len);
            BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream));

            final MediaItem mediaItem = parseHead(reader, buffer);
            //begin
            if (mDataTransferListener != null) {
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDataTransferListener.onBegin(mediaItem);
                    }
                });
            }

            int bodyStart = Utils.locationAfterCRLFCRLF(buffer, 0);
            int bodySize = 0;
            if (bodyStart < len) {
                bodySize = len - bodyStart;
            }

            String shortName = Utils.genValidateFileName(mediaItem.mTitle.trim(), "");
            String extension = Utils.getFileExtension(mediaItem.mData);
            String fileName = shortName + "." + extension;
            String filePath = mStoreDir.endsWith(File.separator) ? (mStoreDir + fileName) : mStoreDir + File.separatorChar + fileName;
            filePath = Utils.genUniqueFilePath(filePath);
            System.out.println("shortName="+shortName+
            		"extension"+extension+"fileName"+fileName+"filePath"+filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(buffer, bodyStart, bodySize);
            fileOutputStream.flush();

            long transferSize = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
                transferSize += len;
                if (mDataTransferListener != null) {
                    final long progress = transferSize;
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDataTransferListener.onProgress(mediaItem, progress, mediaItem.mSize);
                        }
                    });
                }
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            byteArrayInputStream.close();
            inputStream.close();

            if (mDataTransferListener != null) {
                mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mDataTransferListener.onEnd(mediaItem);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSend(OutputStream outputStream) {
    }
}
