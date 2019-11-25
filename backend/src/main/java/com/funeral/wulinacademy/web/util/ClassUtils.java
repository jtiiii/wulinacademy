package com.funeral.wulinacademy.web.util;

/**
 * 类处理工具
 *
 * @author FuneralObjects
 * @date 2019-04-17 02:22
 */
public class ClassUtils extends org.springframework.util.ClassUtils {

    public static boolean isVoid(Class clzz){
        return Void.TYPE.equals(clzz);
    }

    public static boolean isBasic(Class clzz){
        return clzz.isPrimitive();
    }
}
