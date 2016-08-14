package com.example.yang.yige.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.yang.yige.R;
import com.example.yang.yige.utils.FileUtils;
import com.example.yang.yige.utils.OneApi;
import com.example.yang.yige.utils.SnackbarUtil;

/**
 * Created by Yang on 2015/10/4.
 */
public class HomeDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView strAuthor, strTitle, strContent, strContentAuthor;
    private ImageView imgThumb, imgSend, imgDown;
    private String titleAndAuthor, thumbImgUrl, originalImgUrl, content, strMarketTime;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        init();
        getExtrasData();
        setUpData();
    }

    private void init() {
        strAuthor = (TextView) findViewById(R.id.str_author);
        strTitle = (TextView) findViewById(R.id.str_title);
        strContent = (TextView) findViewById(R.id.str_content);
        strContentAuthor = (TextView) findViewById(R.id.str_content_author);
        imgThumb = (ImageView) findViewById(R.id.img_thumb);
        imgSend = (ImageView) findViewById(R.id.img_send);
        imgDown = (ImageView) findViewById(R.id.img_down);
        rl = (RelativeLayout) findViewById(R.id.rl_home_detail);

        imgDown.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        imgThumb.setOnClickListener(this);
    }

    private void getExtrasData() {
        Bundle bundle = getIntent().getExtras();
        titleAndAuthor = bundle.getString("titleAndAuthor");
        thumbImgUrl = bundle.getString("thumbImgUrl");
        originalImgUrl = bundle.getString("originalImgUrl");
        content = bundle.getString("content");
        strMarketTime = bundle.getString("strMarketTime");
    }

    private void setUpData() {
        String authorParts[] = titleAndAuthor.split("&");
        String title = authorParts[0];
        String author = authorParts[1];

        String contentParts[] = content.split("。");
        String content = contentParts[0].replaceAll("，", "\n");
        String contentAuthor = contentParts[1];

        strTitle.setText(title);
        strAuthor.setText(author);
        strContent.setText(content);
        strContentAuthor.setText("—— " + contentAuthor);

        Glide.with(this).load(thumbImgUrl).into(imgThumb);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_down:

                SnackbarUtil.showSnackbar(rl, "图片正在下载中...  ");

                Glide.with(getApplicationContext())
                        .load(originalImgUrl)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                FileUtils.saveImageToGallery(HomeDetailActivity.this, resource, rl);
                            }
                        });
                break;
            case R.id.img_send:
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT, OneApi.getOneTodayHome(strMarketTime.toString()));
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.img_thumb:
                Intent pictureIntent = new Intent(HomeDetailActivity.this, PictureActivity.class);
                pictureIntent.putExtra(PictureActivity.ORIGINAL_IMG_URL, originalImgUrl);
                startActivity(pictureIntent);
            default:
                break;
        }
    }
}














