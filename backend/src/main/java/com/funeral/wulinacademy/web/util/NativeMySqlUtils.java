package com.funeral.wulinacademy.web.util;

import com.alibaba.druid.pool.DruidPooledResultSet;
import com.funeral.wulinacademy.web.common.standard.DateStandard;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author FuneralObjects
 * @date 2019-04-14 03:18
 */
public class NativeMySqlUtils {

    private static final Pattern SEARCH_PARAM = Pattern.compile("[$#]\\{[^{}]*}");

    private static final String PREPARED_PREFIX = "#";
    private static final String NORMAL_PREFIX = "$";

    @SuppressWarnings("unchecked")
    public static <T> RowMapper<T> createRowMapper(Class<T> tClass){
        return (rs, rowNum) -> {
            try {
                if(rs.getMetaData().getColumnCount() == 1){
                    return (T) getObject(rs,1,tClass);
                }
                T instance = tClass.newInstance();
                for (Field field : tClass.getDeclaredFields()) {
                    Method setter = tClass.getMethod(StringUtils.setter(field.getName()),field.getType());
                    setter.invoke(instance,getObjectFromResultSet(rs,getColumnName(field),field.getType()));
                }
                return instance;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new SQLException(e);
            }
        };
    }

    private static Object getObjectFromResultSet(ResultSet rs,String name, Class<?> clzz) throws SQLException {
        if (clzz == Date.class){
            return rs.getTimestamp(name);
        }
        return getObject(rs,name,clzz);
    }

    private static Object getObject(ResultSet rs, String name,Class<?> clzz) throws SQLException {
        if(rs instanceof DruidPooledResultSet){
            return ((DruidPooledResultSet)rs).getRawResultSet().getObject(name,clzz);
        }
        return rs.getObject(name,clzz);
    }

    private static Object getObject(ResultSet rs, int columnIndex, Class<?> clzz) throws SQLException {
        if (rs instanceof DruidPooledResultSet) {
            return ((DruidPooledResultSet) rs).getRawResultSet().getObject(columnIndex);
        }
        return rs.getObject(columnIndex,clzz);
    }
    private static String getColumnName(Field field){
        Column[] columns = field.getAnnotationsByType(Column.class);
        if(columns == null || columns.length == 0 || columns[0] == null){
            return StringUtils.camelToUnderline(field.getName());
        }
        return columns[0].value();
    }

    public static List<String> extractParamString(String sql){
        Matcher m = SEARCH_PARAM.matcher(sql);
        List<String> result = new ArrayList<>();
        while (m.find()){
            result.add(m.group());
        }
        return result;
    }

    public static String convertPropertyToParam(String property,boolean prepared){
        return (prepared ? PREPARED_PREFIX : NORMAL_PREFIX) + "{" + property + "}";
    }

    public static String extractParamName(String paramStr){
        return paramStr.substring(paramStr.indexOf("{")+1,paramStr.lastIndexOf("}"));
    }


    public static String toStringForSqlParam(Object param){
        if(param == null){
            return "";
        }
        if(param instanceof String){
            return (String) param;
        }
        if(param instanceof Date){
            return DateStandard.formatForSqlParam((Date) param);
        }
        if(param instanceof Collection){
            List<String> temps = ((Collection<?>)param).parallelStream().map( NativeMySqlUtils::toStringForSqlParam).collect(Collectors.toList());
            return CollectionUtils.joinWithSeparator( temps, ",");
        }
        return param.toString();
    }



}
