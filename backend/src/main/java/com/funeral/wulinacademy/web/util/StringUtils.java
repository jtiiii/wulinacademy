package com.funeral.wulinacademy.web.util;

/**
 * extension of {@link org.springframework.util.StringUtils }
 * @author FuneralObjects
 * @date 2019-04-14 13:07
 */
public class StringUtils extends org.springframework.util.StringUtils {
    private static final String UNDERLINE = "_";

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
        return gettsetter(name,false);
    }

    public static String setter(String name){
        return gettsetter(name,true);
    }
    private static String gettsetter(String name, boolean setter){
        StringBuilder sb = new StringBuilder(setter?"set":"get").append(name).replace(3,4,name.substring(0,1).toUpperCase());
        return sb.toString();
    }
}
