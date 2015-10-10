package com.example.yang.yige.utils;

import android.util.Log;

import com.example.yang.yige.model.Article;
import com.example.yang.yige.model.Daily;
import com.example.yang.yige.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yang on 2015/10/5.
 */
public class JsonParseUtils {

    private Daily daily;
    private Article article;
    private Question question;
    private static JsonParseUtils jsonParseUtils = null;

    public static JsonParseUtils getInstance(){
        if (jsonParseUtils == null){
            jsonParseUtils = new JsonParseUtils();
        }

        return jsonParseUtils;
    }


    //解析Daily Json数据
    public Daily parseHomeDetail(String url) throws JSONException {

        //data
        JSONObject jsonObject = new JSONObject(url);
        JSONObject object = jsonObject.getJSONObject("hpEntity");
        //获取strHpTitle
        String strHpTitle = object.getString("strHpTitle");
        //获取作品名称 && 作者
        String strAuthor = object.getString("strAuthor");
        //获取首页图片的原始地址
        String strOriginalImgUrl = object.getString("strOriginalImgUrl");
        //获取图首页图片的缩略图地址
        String strThumbnailUrl = object.getString("strThumbnailUrl");
        //获取作品内容
        String strContent = object.getString("strContent");
        String strMarketTime = object.getString("strMarketTime");

        daily = new Daily(strHpTitle,strAuthor,strContent,strThumbnailUrl,strOriginalImgUrl,strMarketTime);

        return daily;
    }

    //解析Article Json数据
    public Article pareseArticleJson(String url) throws JSONException{

        JSONObject object = new JSONObject(url);
        JSONObject jsonObject = object.getJSONObject("contentEntity");

        String articleDate = jsonObject.getString("strContMarketTime");
        String articleContent = jsonObject.getString("strContent");
        String articleTitle = jsonObject.getString("strContTitle");
        String articleContAuthor = jsonObject.getString("strContAuthor");
        String articleIntroducer = jsonObject.getString("strContAuthorIntroduce");
        String articleWeibo = jsonObject.getString("sWbN");
        String author = jsonObject.getString("sAuth");
        String webLink = jsonObject.getString("sWebLk");

        article = new Article(articleTitle,author,articleContent,articleDate,webLink);

        return article;
    }

    //解析"问题" Json
    public Question parseQuestion(String url) throws JSONException{
        JSONObject object = new JSONObject(url);
        JSONObject jsonObject = object.getJSONObject("questionAdEntity");
        String title = jsonObject.getString("strQuestionTitle");
        String webLink = jsonObject.getString("sWebLk");
        String marketTime= jsonObject.getString("strQuestionMarketTime");
        Log.e("marketTime",marketTime);

        question = new Question(marketTime,title,webLink);

        return question;
    }

}
