package com.example.yang.yige.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yang.yige.R;
import com.example.yang.yige.utils.FileUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Yang on 2015/9/30.
 */
public class PictureActivity extends AppCompatActivity{

    public static final String ORIGINAL_IMG_URL = "strOriginalImgUrl";
    private String strOriginalImgUrl;
    private ImageView imgPicture;
    private PhotoViewAttacher photoViewAttacher;
    private RelativeLayout relativeLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        init();
        setUpToolbar();

        parseIntent();
        Picasso.with(this).load(strOriginalImgUrl).into(imgPicture);
        setUpLongClickPicture();

    }

    private void parseIntent(){
        strOriginalImgUrl = getIntent().getStringExtra(ORIGINAL_IMG_URL);
    }

    private void init(){
        imgPicture = (ImageView) findViewById(R.id.img_picture);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_picture);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("长按保存图片");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpLongClickPicture(){
        photoViewAttacher = new PhotoViewAttacher(imgPicture);
        photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pictureAlertDialog();
                return true;
            }

        });
    }

    private void pictureAlertDialog(){
        new android.support.v7.app.AlertDialog.Builder(PictureActivity.this).setMessage("保存图片？")
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("ok","ok_now");
                        //Save Picture
                        DownloadImgTask task = new DownloadImgTask();
                        task.execute();
                    }
                }).show();
    }

    class DownloadImgTask extends AsyncTask<Bitmap,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(Bitmap... params) {
            FileUtils.getInstance().getSnackbar(relativeLayout, "正在下载中哦...");
            return FileUtils.getInstance().getBitmapFromUrl(strOriginalImgUrl);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            try {
                //将图片添加到系统的图库之中
                Log.e("post", "post now 11........");
                FileUtils.getInstance().
                        createImageFile(bitmap, PictureActivity.this);
                //下载成功之后提示
                Log.e("post", "post now ........");
                FileUtils.getInstance().
                        getSnackbarOpenPicture(relativeLayout, PictureActivity.this, "下载完成");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
