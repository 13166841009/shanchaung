package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ruanjianbei.wifi.com.my_setting.util.Tools;
import ruanjianbei.wifi.com.my_setting.util.diolog;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class my_information extends Activity{
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private ImageView iv1;
    private FrameLayout switchAvatar;

    private String[] items = new String[] { "选择本地图片", "拍照" };
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "face.png";
    private static String load = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/shangchuan/data/image/";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        setContentView(R.layout.activity_my_information);
        tv1 = (TextView) findViewById(R.id.nicheng);
        tv2 = (TextView) findViewById(R.id.name);
        tv3 = (TextView) findViewById(R.id.sex);
        tv4 = (TextView) findViewById(R.id.phone);
        tv5 = (TextView) findViewById(R.id.email);
        tv6 = (TextView) findViewById(R.id.address);
        iv1 = (ImageView) findViewById(R.id.userface);
        switchAvatar = (FrameLayout) findViewById(R.id.flayout);
        //获得本地图片
        File file = new File(load+IMAGE_FILE_NAME);
        if (file.exists())
        {
            Bitmap bitmap = getLoacalBitmap(load+IMAGE_FILE_NAME);
            iv1.setImageBitmap(bitmap);
        }
    }
    public void onClicknicheng(View v){
        new diolog().getcontent(my_information.this, tv1);
    }
    public void onClickname(View v){
        new diolog().getcontent(my_information.this, tv2);
    }
    public void onClicksex(View v){
        new diolog().getcontent(my_information.this, tv3);
    }
    public void onClickphone(View v){
        new diolog().getcontent(my_information.this, tv4);
    }
    public void onClickemail(View v){
        new diolog().getcontent(my_information.this, tv5);
    }
    public void onClickaddress(View v){
        new diolog().getcontent(my_information.this, tv6);
    }
    public void onClickTouxiang(View v) {
        showDialog();
    }

    private void showDialog() {
        /**
         * 显示选择对话框
         */
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1:
                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (Tools.hasSdcard()) {

                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(),
                                                    IMAGE_FILE_NAME)));
                                }
                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (Tools.hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory()
                                        + IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(my_information.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            iv1.setImageDrawable(drawable);
            //保存在本地
            FileOutputStream b = null;
            File file = new File(load);
            if (!file.exists())
            {
                file.mkdirs();
            }
            String fileName = load+IMAGE_FILE_NAME;
            try {
                b = new FileOutputStream(fileName);
                photo.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
/*            try
            {
                view.setImageBitmap(bitmap);// 将图片显示在ImageView里
            }catch(Exception e)
            {
                Log.e("error", e.getMessage());
            }*/
        }
    }
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}




