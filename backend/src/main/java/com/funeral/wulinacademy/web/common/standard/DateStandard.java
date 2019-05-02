package com.funeral.wulinacademy.web.common.standard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The standard of date class
 *
 * @author FuneralObjects
 * @date 2019-03-29 15:06
 */
public class DateStandard {

    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SQL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String BEIJING_TIMEZONE = "GMT+8";

    public static Date parseByTimestamp(String str) throws ParseException {
        return new SimpleDateFormat(DATE_TIME_FORMAT).parse(str);
    }

    public static String formatForSqlParam(Date date){
        return new SimpleDateFormat(SQL_DATE_TIME_FORMAT).format(date);
    }

}
