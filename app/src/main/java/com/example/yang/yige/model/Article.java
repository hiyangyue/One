package com.example.yang.yige.model;

/**
 * Created by Yang on 2015/10/3.
 */
public class Article {

    private String title;
    private String author;
    private String content;
    private String date;
    private String webLink;

    public Article(String title, String author, String content, String date,String webLink) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = date;
        this.webLink = webLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getWebLink() {
        return webLink;
    }

}
