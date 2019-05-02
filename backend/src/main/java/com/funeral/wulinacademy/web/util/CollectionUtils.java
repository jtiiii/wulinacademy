package com.funeral.wulinacademy.web.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * extend for spring-util's CollectionUtils
 *
 * @author FuneralObjects
 * @date 2019-03-29 14:49
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {
    public static boolean isEmptyAfterRemoveNull(Map<?,?> map){
        if(map != null){
            map.remove(null);
        }
        return isEmpty( map);
    }

    public static boolean isEmptyAfterRemoveNull(Collection<?> collection){
        if( collection != null){
            collection.removeAll( Collections.singleton( null));
        }
        return isEmpty( collection);
    }

    public static String joinWithSeparator(Collection<?> collection,String separator){
        StringBuilder result = new StringBuilder();
        int i =0;
        for (Object o : collection) {
            if(i > 0){
                result.append(separator);
            }
            result.append(o);
            i++;
        }
        return result.toString();
    }
}
