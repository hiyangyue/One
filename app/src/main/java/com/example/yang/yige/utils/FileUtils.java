package com.example.yang.yige.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yang on 2015/9/19.
 */
public class FileUtils {

    private String currentImgPath;

    //单例设计模式
    private static FileUtils fileUtils = null;

    public static FileUtils getInstance(){
        if (fileUtils == null){
            fileUtils = new FileUtils();
        }
        return fileUtils;
    }


    //将图片地址解析成图片
    public Bitmap getBitmapFromUrl(String bitmapUrl){

        try {
            URL url = new URL(bitmapUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            InputStream is = httpURLConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);

            if (bitmap!=null){
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public File createImageFile(Bitmap bitmap,Activity activity) throws IOException{
        String timeStap = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String imgFileName = "ONE_" + timeStap + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File image = File.createTempFile(
                imgFileName,
                ".jpg",
                storageDir
        );

        FileOutputStream fos = new FileOutputStream(image);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();

        currentImgPath = "file:" + image.getAbsolutePath();

        addGalleryPic(currentImgPath,activity);

        return image;
    }

    //添加到画廊
    private void addGalleryPic(String fileImgaPath,Activity activity){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(fileImgaPath);
        Uri contentUri = Uri.fromFile(file);
        intent.setData(contentUri);
        activity.sendBroadcast(intent);
    }

    public String getCurrentImgPath(){
        return currentImgPath;
    }

    public void getSnackbar(View view,String snackerInfo){
        Snackbar snackbar = Snackbar.make(view, snackerInfo, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void getSnackbarOpenPicture(View view, final Activity activity,String snackerInfo){
        Snackbar snackbar  = Snackbar.make(view, snackerInfo, Snackbar.LENGTH_SHORT);
        snackbar.show();
        snackbar.setAction("打开图片", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                currentImgPath = FileUtils.getInstance().getCurrentImgPath();
                intent.setDataAndType(Uri.parse(currentImgPath), "image/*");
                activity.startActivity(intent);
            }
        });
    }

}
