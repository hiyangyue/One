package com.example.yang.yige.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yang.yige.R;
import com.example.yang.yige.activity.ArticleWebActivity;
import com.example.yang.yige.model.Article;

import java.util.List;

/**
 * Created by Yang on 2015/10/6.
 */
public class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.ViewHolder> {

    private List<Article> articleList;
    private Context context;

    public ArticleItemAdapter(Context context,List<Article> articleList){
        this.context = context;
        this.articleList = articleList;
    }

    @Override
    public ArticleItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_article, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleItemAdapter.ViewHolder holder, int position) {
        Article article = articleList.get(position);

        TextView articleTitle = holder.articleTitle;
        TextView articleAuthor = holder.articleAuthor;
        TextView articleContent = holder.articleContent;
        TextView articleDate = holder.articleDate;

        String authorParts[] = article.getAuthor().split("。");
        String author = authorParts[0];

        String dateParts[] = article.getDate().split(" ");
        String date = dateParts[0];

        articleTitle.setText(article.getTitle());
        articleAuthor.setText(author);

//        String contentArticle = article.getContent().replaceAll("<br>","\n");
//        articleContent.setText(contentArticle.replaceAll("1",""));
        articleContent.setText(article.getContent().replaceAll("<br>","\n"));
        articleDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView articleTitle,articleContent,articleAuthor,articleDate;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            articleTitle = (TextView) itemView.findViewById(R.id.article_title);
            articleAuthor = (TextView) itemView.findViewById(R.id.article_author);
            articleDate = (TextView) itemView.findViewById(R.id.article_date);
            articleContent = (TextView) itemView.findViewById(R.id.article_content);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.article_relativeLayout);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article article = articleList.get(getLayoutPosition());
                    startArticleDetailActivity(article.getWebLink());
                }
            });

        }

        private void startArticleDetailActivity(String artilceUrl){
            Intent intent = new Intent(context, ArticleWebActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("article_url",artilceUrl);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }
}
