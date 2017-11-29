package com.wollon.tourgass.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 漫聆默 on 2017/11/29 0029.
 */

public class DateUtil {
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
    public static String dateToStrShort(Date dateDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=formatter.format(dateDate);

        return dateString;
    }
    public static Date strToDateShort(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            // Fri Feb 24 00:00:00 CST 2012
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 2012-02-24
        date = java.sql.Date.valueOf(str);

        return date;
    }
}
