package com.example.yang.yige.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yang.yige.R;
import com.example.yang.yige.adapter.ArticleItemAdapter;
import com.example.yang.yige.model.Article;
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
 * Created by Yang on 2015/10/6.
 */
public class ArticleFragment extends Fragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Article> articleList = new ArrayList<>();
    private ArticleItemAdapter adapter;
    private String date;
    private int count = 0;

    private boolean loading = true;
    private int firstVisiblesItems,visibleCount,totalItemCount;
    private int previousTotal = 0;

    private AsyncHttpClient client = new AsyncHttpClient();
    private AsyncHttpResponseHandler handler = new BaseJsonHttpResponseHandler<Article>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Article response) {
            Log.e("success","success");

            String title = response.getTitle();
            String aurthor = response.getAuthor();
            String content = response.getContent();
            String date = response.getDate();
            String webLink = response.getWebLink();

            Article article = new Article(title,aurthor,content,date,webLink);
            articleList.add(article);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Article errorResponse) {
            Log.e("Failure","Something wrong...");
        }

        @Override
        protected Article parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Article article = JsonParseUtils.getInstance().
                    pareseArticleJson(rawJsonData);
            return article;
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
        setUpArticleData();

        return view;
    }

    private void init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_container);
    }

    private void setUpRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ArticleItemAdapter(getActivity(),articleList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }

                if (!loading && (totalItemCount - visibleCount) <= (firstVisiblesItems + visibleCount)) {

                    client.get(getActivity(), OneApi.getOneTodayArticle(date, count),handler);
                    //设置最新的count
                    count = count +1;

                    loading = true;
                }
            }
        });

    }

    private void setUpArticleData(){
        for (int i = 1 ; i <= 3 ; i ++){
            client.get(getActivity(), OneApi.getOneTodayArticle(date,count),handler);
            count = count + 1;
        }
    }

}














