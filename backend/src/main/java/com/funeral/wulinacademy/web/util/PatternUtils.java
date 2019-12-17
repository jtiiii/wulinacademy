package com.funeral.wulinacademy.web.util;

import java.util.regex.Pattern;

/**
 * @author FuneralObjects
 * CreateTime 2019-04-17 22:30
 */
public class PatternUtils {
    /**
     * 日期正则表达式 yyyy-MM-dd
     * 包括瑞年的校验
     */
    private static final String REGEX_FULL_DATE = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|([0-9]{2}([02468][048]|[13579][26])-02-(0[1-9]|[1][0-9]|2[0-9]))";
    /**
     * 时间戳的正则表达式 yyyy-MM-dd'T'HH:mm:ss
     * 包括瑞年的校验
     */
    private static final String REGEX_DATE_TIME = REGEX_FULL_DATE+"T(([01][0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]";

    /**
     * 全数字 正则表达式，不限长度
     * E.g.
     * .3433
     * .00
     * +.232
     * -.1231
     * +12312
     * 389234
     * -4344
     * +1233.45
     * -3535.44555
     */
    private static final String REGEX_ALL_NUMBER = "[-+]?(([0-9]+(\\.[0-9]+)?)|(\\.[0-9]+))";

    public static final Pattern ALL_NUMBER = Pattern.compile(REGEX_ALL_NUMBER);
    public static final Pattern FULL_DATE = Pattern.compile(REGEX_FULL_DATE);
    public static final Pattern DATE_TIME = Pattern.compile(REGEX_DATE_TIME);


    public static boolean isNumeric(String str){
        return ALL_NUMBER.matcher(str).matches();
    }

}
