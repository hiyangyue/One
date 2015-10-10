package com.example.yang.yige.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.yang.yige.R;

/**
 * Created by Yang on 2015/10/10.
 */
public class AboutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout tablayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setUpToolbar();
    }

    private void setUpToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        tablayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        tablayout.setTitle("关 于");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
