package com.example.yang.yige.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yang.yige.R;
import com.example.yang.yige.adapter.HomeItemAdapter;
import com.example.yang.yige.listener.EndlessRecyclerOnScrollListener;
import com.example.yang.yige.model.Daily;
import com.example.yang.yige.utils.DateUtils;
import com.example.yang.yige.utils.JsonParseUtils;
import com.example.yang.yige.utils.OneApi;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Yang on 2015/9/29.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Daily> dailyList = new ArrayList<>();
    private HomeItemAdapter adapter;
    private String date;
    private Daily daily;

    AsyncHttpClient client = new AsyncHttpClient();
    AsyncHttpResponseHandler handler = new BaseJsonHttpResponseHandler<Daily>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Daily response) {

            String title = response.getHpTitle();
            String thumbImgUrl = response.getThumbImgUrl();
            Log.e("thumbUmgUrl", thumbImgUrl);
            String strOriginalImgUrl = response.getStrOriginalImgUrl();
            String content = response.getContent();
            String author = response.getAuthor();
            String strMarketTime = response.getStrMarketTime();
            daily = new Daily(title, author, content, thumbImgUrl, strOriginalImgUrl, strMarketTime);

            dailyList.add(daily);
            adapter.notifyItemRangeInserted(dailyList.size(), 1);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Daily errorResponse) {
            Log.e("faliture", "so sad. :( ");
        }

        @Override
        protected Daily parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            return JsonParseUtils.getInstance().parseHomeDetail(rawJsonData);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        date = bundle.getString("date");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_home, container, false);

        init(view);
        setUpRecyclerView();
        addItem();

        return view;
    }

    private void setUpRecyclerView() {

        //网格布局，用来显示瀑布流
        final LinearLayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setHasFixedSize(true);
        adapter = new HomeItemAdapter(getContext(), setUpData());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                client.get(getActivity(), OneApi.getOneTodayHome(date), handler);
                //设置最新的时间
                date = DateUtils.parseDate(date);
            }
        });
    }


    private List<Daily> setUpData() {
        dailyList = new ArrayList<>();
        return dailyList;
    }

    public void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_container);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
    }

    private void addItem(){

        for (int i = 0; i < 3; i++) {
            client.get(getActivity(), OneApi.getOneTodayHome(date), handler);
            date = DateUtils.parseDate(date);
        }
    }

    @Override
    public void onRefresh() {
        Snackbar snackbar = Snackbar.make(recyclerView, "已经是最新的咯 :) ", Snackbar.LENGTH_SHORT);
        snackbar.show();
        swipeRefreshLayout.setRefreshing(false);
    }
}
