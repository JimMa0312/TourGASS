package com.wollon.tourgass.util;

import java.text.SimpleDateFormat;

/**
 * Created by 漫聆默 on 2017/11/29 0029.
 */

public class DateUtils {
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }
}
