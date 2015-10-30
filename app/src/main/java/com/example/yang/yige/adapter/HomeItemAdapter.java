package com.example.yang.yige.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yang.yige.R;
import com.example.yang.yige.activity.HomeDetailActivity;
import com.example.yang.yige.model.Daily;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Yang on 2015/9/30.
 */
public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder>{

    private List<Daily> dailyList;
    private Context context;
    private String thumbImgUrl;
    private String strOriginalImgUrl;

    public HomeItemAdapter(Context context,List<Daily> dailyList){
        this.context = context;
        this.dailyList = dailyList;
    }

    @Override
    public HomeItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_home, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeItemAdapter.ViewHolder viewHolder, int i) {
        Daily daily = dailyList.get(i);

        //date
        TextView textDate = viewHolder.textDate;
        textDate.setText(daily.getHpTitle());

        //thumbImg
        ImageView thumbImg = viewHolder.imgThumb;
        if (thumbImg == null){
            thumbImg = new ImageView(context);
        }

//        Picasso picasso = Picasso.with(context);
//        //开启调试模式
//        picasso.setIndicatorsEnabled(true);
//        picasso.setLoggingEnabled(true);
//
//        picasso.with(context).load(daily.getThumbImgUrl()).
//                into(thumbImg);
        Glide.with(context)
                .load(daily.getThumbImgUrl())
                .into(thumbImg);

        //originalUrl
        strOriginalImgUrl = daily.getStrOriginalImgUrl();

        //content && title
        String contentAuthorAndTitle = daily.getContent();
        String contentPart[] = contentAuthorAndTitle.split("。");
        String content = contentPart[0].replaceAll("，","\n");
        if (content.contains("。")){
            content.replaceAll("。","\n");
        }

        //author
        TextView textAuthor = viewHolder.textAuthor;
        String titleAndAuthor = daily.getAuthor();
        String parts[] = titleAndAuthor.split("&");
        String title = parts[0];
        textAuthor.setText(title);

    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView textDate,textAuthor;
        ImageView imgThumb;

        public ViewHolder(View view){
            super(view);
            textDate = (TextView) view.findViewById(R.id.tv_daily_date);
            imgThumb = (ImageView) view.findViewById(R.id.img_thumb);
            textAuthor = (TextView) view.findViewById(R.id.tv_daily_title);

            imgThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Daily daily = dailyList.get(getLayoutPosition());
                    String titleAndAuthor = daily.getAuthor();
                    String thumbImgUrl = daily.getThumbImgUrl();
                    String originalImgUrl = daily.getStrOriginalImgUrl();
                    String content = daily.getContent();

                    Bundle bundle = new Bundle();
                    bundle.putString("titleAndAuthor", titleAndAuthor);
                    bundle.putString("thumbImgUrl",thumbImgUrl);
                    bundle.putString("originalImgUrl", originalImgUrl);
                    bundle.putString("content", content);
                    bundle.putString("strMarketTime", daily.getStrMarketTime());

                    Intent intent = new Intent(context,HomeDetailActivity.class);
                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });

        }
    }
}
