package com.example.yang.yige.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yang.yige.R;
import com.example.yang.yige.activity.QuestionWebActivity;
import com.example.yang.yige.model.Question;

import java.util.List;

/**
 * Created by Yang on 2015/10/7.
 */
public class QuestionItemAdapter extends RecyclerView.Adapter<QuestionItemAdapter.ViewHolder> {

    private List<Question> questionList;
    private Context context;

    public QuestionItemAdapter(Context context,List<Question> questionList){
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public QuestionItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_question, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionItemAdapter.ViewHolder holder, int position) {
        Question question = questionList.get(position);

        TextView title = holder.title;
        title.setText(question.getMarketTime() + "   " + question.getTitle());

    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.question_title);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_question);
        }

        @Override
        public void onClick(View view) {
            Question question = questionList.get(getLayoutPosition());
            startWebActivity(question.getQuestionUrl());
        }
    }

    private void startWebActivity(String url){
        Bundle bundle = new Bundle();
        bundle.putString("question_url",url);
        Intent intent = new Intent(context,QuestionWebActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
