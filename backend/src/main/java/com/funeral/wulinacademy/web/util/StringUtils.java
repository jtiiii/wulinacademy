package com.funeral.wulinacademy.web.util;

import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * extension of {@link org.springframework.util.StringUtils }
 * @author FuneralObjects
 * CreateTime 2019-04-14 13:07
 */
public class StringUtils extends org.springframework.util.StringUtils {
    private static final String UNDERLINE = "_";

    private static final Character MATCH_CHAR = '%';
    private static final List<String> EMPTY_KEYWORDS_LIST = Arrays.asList("%%", "%");

    public static String camelToUnderline(String str){
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if( c >= 'A' && c <= 'Z'){
                result.append(UNDERLINE).append(Character.toLowerCase(c));
            }else{
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String underlineToCamel(String str){
        StringBuilder result = new StringBuilder();
        int first = 0;
        for (String s : str.split(UNDERLINE)) {
            if(first > 0){
                char c = s.charAt(0);
                result.append(Character.toUpperCase(c)).append(s.substring(1));
            }else {
                result.append(s);
            }
            first++;
        }
        return result.toString();
    }

    public static String getter(String name){
        return getterSetter(name,false);
    }

    public static String setter(String name){
        return getterSetter(name,true);
    }

    /**
     * 判断是否为空的关键字，如 “%%”，“%” 为全查询的也同时视为【空】
     *
     * @param keywords 关键字
     * @return true - 为空关键字
     */
    public static boolean isEmptyKeywords(String keywords) {
        return isEmpty(keywords) || EMPTY_KEYWORDS_LIST.contains(keywords.trim());
    }

    /**
     * 模糊全匹配处理
     *
     * @param keywords 关键字
     * @return 处理后的关键字
     */
    public static String processFuzzyFullMatch(String keywords) {
        Assert.isTrue(!isEmptyKeywords(keywords), "Arg[keywords] cannot be blank！");
        boolean hasFirst = MATCH_CHAR.equals(keywords.charAt(0));
        boolean hasLast = MATCH_CHAR.equals(keywords.charAt(keywords.length() - 1));
        if (!hasFirst && !hasLast) {
            return "%" + keywords + "%";
        }
        if (hasFirst && !hasLast) {
            return keywords + "%";
        }
        if (!hasFirst) {
            return "%" + keywords;
        }
        return keywords;
    }
    private static String getterSetter(String name, boolean setter){
        StringBuilder sb = new StringBuilder(setter?"set":"get").append(name).replace(3,4,name.substring(0,1).toUpperCase());
        return sb.toString();
    }
}
