package com.example.yang.yige.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by yueyang on 16/8/14.
 */
public class SnackbarUtil {

    public static void showSnackbar(View view, String snackerInfo){
        Snackbar snackbar = Snackbar.make(view, snackerInfo, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void openPicture(View view, final Context context, String snackerInfo, final String currentImgPath){
        Snackbar snackbar  = Snackbar.make(view, snackerInfo, Snackbar.LENGTH_SHORT);
        snackbar.show();
        snackbar.setAction("打开图片", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(currentImgPath), "image/*");
                context.startActivity(intent);
            }
        });
    }
}
