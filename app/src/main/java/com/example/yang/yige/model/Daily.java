package com.example.yang.yige.model;

import java.io.Serializable;

/**
 * Created by Yang on 2015/9/19.
 */
public class Daily implements Serializable{

    /**
     *  hpTitle : Vol.1077
     *  author  : 星期天聚会&梁浩作品
     *
     */

    private String author;
    private String hpTitle;
    private String thumbImgUrl;
    private String strOriginalImgUrl;
    private String content;
    private String strMarketTime;

    public String getThumbImgUrl() {
        return thumbImgUrl;
    }

    public String getStrMarketTime() {
        return strMarketTime;
    }

    public String getStrOriginalImgUrl() {
        return strOriginalImgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Daily(String hpTitle,String author,String content,String thumbImgUrl,String strOriginalImgUrl,String strMarketTime){
        this.hpTitle = hpTitle;
        this.thumbImgUrl = thumbImgUrl;
        this.content = content;
        this.author = author;
        this.strOriginalImgUrl = strOriginalImgUrl;
        this.strMarketTime = strMarketTime;
    }

    public String getHpTitle() {
        return hpTitle;
    }

    public String getAuthor() {
        return author;
    }
}
