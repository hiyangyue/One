package com.example.yang.yige.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.yang.yige.R;

/**
 * Created by Yang on 2015/10/6.
 */
public class ArticleWebActivity extends AppCompatActivity {

    private String articleUrl;
    private ProgressBar progressBar;
    private WebView webView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        init();
        getExtraData();
        setUpToolbar();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new ViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(articleUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.menu_share:
                Intent intent = new Intent();
                intent.putExtra(Intent.EXTRA_TEXT,articleUrl);
                intent.setType("text/plain");
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else if (newProgress != 100) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    private class ViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /**
             * 这个移除 web顶部，底部的Bar和下载 有点粗暴
             * 应该还有更合适解决方案，
             * 暂时还没有想到
             */
            view.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('header')[0].style.display = 'none'; " +
                    "})()");
            view.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('footer')[0].style.display = 'none'; " +
                    "})()");
        }

    }


    private void init(){
        progressBar = (ProgressBar) findViewById(R.id.article_progressbar);
        webView = (WebView) findViewById(R.id.article_webView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getExtraData(){
        Bundle bundle = getIntent().getExtras();
        articleUrl = bundle.getString("article_url");
        Log.e("articleUrl",articleUrl);
    }
}
