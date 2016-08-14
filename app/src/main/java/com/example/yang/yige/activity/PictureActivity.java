package com.example.yang.yige.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.yang.yige.R;
import com.example.yang.yige.utils.FileUtils;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Yang on 2015/9/30.
 */
public class PictureActivity extends AppCompatActivity {

    public static final String ORIGINAL_IMG_URL = "strOriginalImgUrl";
    private String strOriginalImgUrl;
    private PhotoView imgPicture;

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
        Glide.with(this).load(strOriginalImgUrl).into(imgPicture);
        setUpLongClickPicture();

    }

    private void parseIntent() {
        strOriginalImgUrl = getIntent().getStringExtra(ORIGINAL_IMG_URL);
    }

    private void init() {
        imgPicture = (PhotoView) findViewById(R.id.img_picture);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_picture);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("长按保存图片");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpLongClickPicture() {
        photoViewAttacher = new PhotoViewAttacher(imgPicture);
        photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pictureAlertDialog();
                return true;
            }

        });
    }

    private void pictureAlertDialog() {
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
                        //Save Picture
                        Glide.with(getApplicationContext())
                                .load(strOriginalImgUrl)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                        FileUtils.saveImageToGallery(PictureActivity.this, resource, relativeLayout);
                                    }
                                });
                    }
                }).show();
    }


}
