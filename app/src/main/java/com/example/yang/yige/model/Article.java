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

    public void setAuthor(String author) {
        this.author = author;
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

    public void setDate(String date) {
        this.date = date;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
