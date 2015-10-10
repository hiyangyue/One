package com.example.yang.yige.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yang.yige.R;
import com.example.yang.yige.fragment.ArticleFragment;
import com.example.yang.yige.fragment.HomeFragment;
import com.example.yang.yige.fragment.QuestionFragment;
import com.example.yang.yige.utils.DateUtils;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setUpToolbar();
        setUpViewPager();
        setUpTabLayout();
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    }

    private void setUpToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("一 个");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_about:
                        startActivity(new Intent(MainActivity.this,AboutActivity.class));
                        break;
                }

                return true;
            }
        });
    }

    private void setUpViewPager(){
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void setUpTabLayout(){

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public class MyFragmentAdapter extends FragmentStatePagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[] { "首 页","文 章","问 题" };

        public MyFragmentAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            Date date = new Date();
            String parseDate = DateUtils.getDate(date);
            Bundle bundle = new Bundle();
            bundle.putString("date",parseDate);

            switch (i){
                case 0:
                    HomeFragment hf = new HomeFragment();
                    hf.setArguments(bundle);

                    return hf;
                case 1:
                    ArticleFragment af = new ArticleFragment();
                    af.setArguments(bundle);

                    return af;
                case 2:
                    QuestionFragment qf = new QuestionFragment();
                    qf.setArguments(bundle);

                    return qf;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }


}
