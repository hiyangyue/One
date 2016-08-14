package com.example.yang.yige.utils;

/**
 * 由于One Api的限制，文章和问答 只能获取到最近十天的数据,见谅
 */
public class OneApi {

    public static String getOneTodayHome(String date){
        return "http://rest.wufazhuce.com/OneForWeb/one/getHp_N?strDate=" + date +"&strRow=1";
    }

    public static String getOneTodayArticle(String date,int count){
        return "http://rest.wufazhuce.com/OneForWeb/one/getC_N?strDate=" + date + "&strRow=" + count;
    }

    public static String getOneTodayQuestion(String date,int count){
        return "http://rest.wufazhuce.com/OneForWeb/one/getQ_N?strDate="+ date+ "&strRow=" + count;
    }

}
