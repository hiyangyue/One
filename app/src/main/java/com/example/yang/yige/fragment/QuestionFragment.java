package com.example.yang.yige.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yang.yige.R;
import com.example.yang.yige.adapter.QuestionItemAdapter;
import com.example.yang.yige.listener.EndlessRecyclerOnScrollListener;
import com.example.yang.yige.model.Question;
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
 * Created by Yang on 2015/10/7.
 */
public class QuestionFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Question> questionList = new ArrayList<>();
    private QuestionItemAdapter adapter;
    private String date;
    private int count = 1;

    private AsyncHttpClient client = new AsyncHttpClient();
    private AsyncHttpResponseHandler handler = new BaseJsonHttpResponseHandler<Question>() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, Question response) {
            Question question = new Question(response.getMarketTime(),response.getTitle(),response.getQuestionUrl());
            questionList.add(question);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, Question errorResponse) {
            Log.e("failure"," :( ");
        }

        @Override
        protected Question parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Question question = JsonParseUtils.getInstance().parseQuestion(rawJsonData);
            return question;
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
        setUpQuestionData();

        return view;
    }

    private void init(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void setUpRecyclerView(){
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new QuestionItemAdapter(getActivity(),questionList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                client.get(getActivity(), OneApi.getOneTodayQuestion(date, count),handler);
                //设置最新的count
                count = count + 1;
            }
        });
    }

    private void setUpQuestionData(){
        for (int i = 1 ; i <= 3 ; i ++){
            client.get(getActivity(), OneApi.getOneTodayQuestion(date, count),handler);
            count = count + 1;
        }
    }

}
