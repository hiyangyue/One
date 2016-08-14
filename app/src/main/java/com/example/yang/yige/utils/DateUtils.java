package com.example.yang.yige.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Yang on 2015/10/5.
 */
public class DateUtils {

    public static String getDate(Date date){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = format.format(date);

        return formatDate;
    }

    public static String parseDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = df.parse(date);
            System.out.println("d1==" + df.format(d1));
            Calendar g = Calendar.getInstance();
            g.setTime(d1);
            g.add(Calendar.DATE, -1);
            Date d2 = g.getTime();
            return df.format(d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }



}
