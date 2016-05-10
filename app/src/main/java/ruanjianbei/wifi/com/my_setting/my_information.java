package ruanjianbei.wifi.com.my_setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private String[] items = new String[] { "选择本地图片", "拍照" };
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "face.png";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final int SELECT_PIC_KITKAT = 3;
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
        db = new DBServiceOperate(my_information.this);
        UpdataInformation();
    }
    public void UpdataInformation(){
        String nicheng = null,name = null,sex = null,number = null,address = null,email = null;
        Cursor cursor =  db.selectInformation();
        //取出头像
        byte[] photo = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {//just need to query one time
                //取出图片
                photo = cursor.getBlob(cursor.getColumnIndex("Photo"));
                //取出其他信息
                nicheng = cursor.getString(cursor.getColumnIndex("Nicheng"));
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
        tv1.setText(nicheng);
        tv2.setText(name);
        tv3.setText(sex);
        tv4.setText(number);
        tv5.setText(email);
        tv6.setText(address);
    }
    public void onClicknicheng(View v){
        String LEIXING = "Nicheng";
        new diolog().getContent(my_information.this, tv1, LEIXING, db);
    }
    public void onClickname(View v){
        String LEIXING = "Name";
        new diolog().getContent(my_information.this, tv2,LEIXING,db);
    }
    public void onClicksex(View v){
        String LEIXING = "Sex";
        new diolog().getSEX(my_information.this,tv3,LEIXING,db);
    }
    public void onClickphone(View v){
        String LEIXING = "Number";
        new diolog().getContent(my_information.this, tv4,LEIXING,db);
    }
    public void onClickemail(View v){
        String LEIXING = "Email";
        new diolog().getContent(my_information.this, tv5,LEIXING,db);
    }
    public void onClickaddress(View v){
        String LEIXING = "Email";
        new diolog().getContent(my_information.this, tv6,LEIXING,db);
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
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                    startActivityForResult(intent,SELECT_PIC_KITKAT);
                                } else {
                                    startActivityForResult(intent,IMAGE_REQUEST_CODE);
                                }
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
                case SELECT_PIC_KITKAT:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (Tools.hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory().getAbsolutePath()
                                ,IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(my_information.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {

                        getImageToView(data,iv1);
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
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url=getPath(my_information.this,uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        }else {
            intent.setDataAndType(uri, "image/*");
        }
// 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    private void getImageToView(Intent data,ImageView iv1) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            iv1.setImageDrawable(drawable);
            db.upDateInformation(photo);
        }
    }
/*        public static Bitmap getLoacalBitmap(String url) {
            try {
                FileInputStream fis = new FileInputStream(url);
                return BitmapFactory.decodeStream(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }*/
//以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
@SuppressLint("NewApi")
public static String getPath(final Context context, final Uri uri) {

    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if (isExternalStorageDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }

        }
        // DownloadsProvider
        else if (isDownloadsDocument(uri)) {
            final String id = DocumentsContract.getDocumentId(uri);
            final Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            return getDataColumn(context, contentUri, null, null);
        }
        // MediaProvider
        else if (isMediaDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    split[1]
            };

            return getDataColumn(context, contentUri, selection, selectionArgs);
        }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {
        // Return the remote address
        if (isGooglePhotosUri(uri))
            return uri.getLastPathSegment();

        return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
        return uri.getPath();
    }

    return null;
}

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}




