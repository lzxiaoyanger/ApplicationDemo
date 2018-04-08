package com.zz.personal.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zz on 2017.06.12.
 */
public class DateUtil {

//    public static Date StrToDate(String dateStr) throws ParseException {
//        return com.zbj.commons.util.DateUtil.strToDate(dateStr,"yyyy-MM-dd HH:mm:ss");
//    }
//
//    public static Date StrToDateNoSS(String dateStr) throws ParseException {
//        return com.zbj.commons.util.DateUtil.strToDate(dateStr,"yyyy-MM-dd HH:mm");
//    }

    public static String DateToStr(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat ymdhmsFormat = new SimpleDateFormat(pattern);
        return  ymdhmsFormat.format(date);
    }


}
