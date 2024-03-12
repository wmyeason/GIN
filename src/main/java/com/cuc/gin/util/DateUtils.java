package com.cuc.gin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : rf.
 * @since : 2024/3/11,
 **/
public class DateUtils {
    /*
    * 输入 2024-02-27T16:00:00.000Z
    * 输出 2024-02-27
    *
    * */
    public static String format(String dateString){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
