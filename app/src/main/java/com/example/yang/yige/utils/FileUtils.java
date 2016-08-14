package com.example.yang.yige.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yang on 2015/9/19.
 */
public class FileUtils {

    public static void saveImageToGallery(Context context, Bitmap bmp,View view) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "One");
        if (!appDir.exists()) {
            appDir.mkdir();
        }

        String timeStap = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = "ONE_" + timeStap + "_" + ".jpg";

        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SnackbarUtil.openPicture(view,context,"打开图片","file:" + file.getAbsolutePath());

        addGalleryPic(file.getAbsolutePath(),(Activity) context);
    }

    public static void addGalleryPic(String fileImagePath,Activity activity){
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, fileImagePath);

        activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}
