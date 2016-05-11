package ruanjianbei.wifi.com.my_setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import ruanjianbei.wifi.com.my_setting.util.DBServiceOperate;
import ruanjianbei.wifi.com.my_setting.util.Tools;
import ruanjianbei.wifi.com.my_setting.util.diolog;
import ruanjianbei.wifi.com.shanchuang.R;

/**
 * Created by linankun1 on 2016/5/1.
 */
public class my_information extends Activity{
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    private ImageView iv1;
    private FrameLayout switchAvatar;
    private DBServiceOperate db;
    private String[] items = new String[] { "选择本地图片", "拍照","取消" };
    private String[] items_two = new String[]{"男","女","取消" };
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "face.png";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
        setContentView(R.layout.activity_my_information);
        //tv1 = (TextView) findViewById(R.id.nicheng);
        tv2 = (TextView) findViewById(R.id.name);
        tv3 = (TextView) findViewById(R.id.sex);
        tv4 = (TextView) findViewById(R.id.phone);
        tv5 = (TextView) findViewById(R.id.email);
        tv6 = (TextView) findViewById(R.id.address);
        iv1 = (ImageView) findViewById(R.id.userface);
        switchAvatar = (FrameLayout) findViewById(R.id.flayout);
        db = new DBServiceOperate(my_information.this);
        UpdataInformation();
    }
    public void UpdataInformation(){
        String name = null,sex = null,number = null,address = null,email = null;
        Cursor cursor =  db.selectInformation();
        //取出头像
        byte[] photo = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {//just need to query one time
                //取出图片
                photo = cursor.getBlob(cursor.getColumnIndex("Photo"));
                //取出其他信息
                //nicheng = cursor.getString(cursor.getColumnIndex("Nicheng"));
                name = cursor.getString(cursor.getColumnIndex("Name"));
                sex = cursor.getString(cursor.getColumnIndex("Sex"));
                number = cursor.getString(cursor.getColumnIndex("Number"));
                email = cursor.getString(cursor.getColumnIndex("Email"));
                address = cursor.getString(cursor.getColumnIndex("Address"));
            }
        }
        ByteArrayInputStream bais = null;
        if (photo != null) {
            bais = new ByteArrayInputStream(photo);
            Drawable drawable = Drawable.createFromStream(bais, "Photo");
            iv1.setImageDrawable(drawable);//把图片设置到ImageView对象中
        }
        if (cursor != null) {
            cursor.close();
        }
        //tv1.setText(nicheng);
        tv2.setText(name);
        tv3.setText(sex);
        tv4.setText(number);
        tv5.setText(email);
        tv6.setText(address);
    }
    //    public void onClicknicheng(View v){
//        String LEIXING = "Nicheng";
//        new diolog().getContent(My_information.this, tv1, LEIXING, db);
//    }
    public void onClickname(View v){
        String LEIXING = "Name";
        new diolog().getContent(my_information.this, tv2,LEIXING,db);
    }
    public void onClicksex(View v){
        DialogSelect(items_two, 1);
        //String LEIXING = "Sex";
        //new diolog().getSEX(My_information.this, tv3, LEIXING, db);
    }
    public void onClickphone(View v){
        String LEIXING = "Number";
        new diolog().getContent(my_information.this, tv4, LEIXING, db);
    }
    public void onClickemail(View v){
        String LEIXING = "Email";
        new diolog().getContent(my_information.this, tv5, LEIXING, db);
    }
    public void onClickaddress(View v){
        String LEIXING = "Email";
        new diolog().getContent(my_information.this, tv6,LEIXING,db);
    }
    public void onClickTouxiang(View v) {
        //0表示图像
        DialogSelect(items, 0);
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

    /**
     * 底部弹出框选项
     */

    public void DialogSelect(final String item[], final int id){
        final Dialog bottomDialog = new Dialog(this,R.style.MaterialDialogSheet);
        View view = getLayoutInflater().inflate(R.layout.sheet_bottom_pickfour,null);
        final TextView[] tvArray = {null,null,null,null};
        tvArray [0] = (TextView) view.findViewById(R.id.tv_one);
        tvArray [1] = (TextView) view.findViewById(R.id.tv_two);
        tvArray [2] = (TextView) view.findViewById(R.id.tv_three);
        tvArray [3] = (TextView) view.findViewById(R.id.tv_four);
        for(int i=0;i<item.length;i++){
            tvArray[i].setVisibility(View.VISIBLE);
            tvArray[i].setText(item[i]);
        }
        bottomDialog.setContentView(view);
        bottomDialog.setCancelable(true);
        bottomDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.show();
        for(int i = 0;i<item.length;i++){
            final int which = i;
            tvArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (id){
                        case 0:
                            switch (which){
                                case 0:
                                    Intent intentFromGallery = new Intent();
                                    intentFromGallery.setType("image/*"); // 设置文件类型
                                    intentFromGallery
                                            .setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(intentFromGallery,
                                            IMAGE_REQUEST_CODE);
                                    bottomDialog.dismiss();
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
                                    bottomDialog.dismiss();
                                    break;
                                case 2:
                                    bottomDialog.dismiss();
                            }

                            break;
                        case  1:
                            switch (which){
                                case 0:
                                    tv3.setText(tvArray[which].getText());
                                    DBServiceOperate db = new DBServiceOperate(getApplicationContext());
                                    db.upDateInformation("Sex", String.valueOf(tvArray[which].getText()));
                                    bottomDialog.dismiss();
                                    break;
                                case 1:
                                    tv3.setText(tvArray[which].getText());
                                    DBServiceOperate db2 = new DBServiceOperate(getApplicationContext());
                                    db2.upDateInformation("Sex", String.valueOf(tvArray[which].getText()));
                                    bottomDialog.dismiss();
                                    break;
                                case 2:
                                    bottomDialog.dismiss();
                                    break;
                            }
                            break;
                    }
                }
            });
        }
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
            db.upDateInformation(photo);
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
