package com.funeral.wulinacademy.web.repository.sql;

import com.funeral.wulinacademy.web.common.standard.DateStandard;
import com.funeral.wulinacademy.web.util.CollectionUtils;
import com.funeral.wulinacademy.web.util.NativeMySqlUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FuneralObjects
 * @date 2019-04-14 09:37
 */
public class SqlStatement{
    private final StringBuilder statement = new StringBuilder();
    private final JdbcTemplate jdbcTemplate;
    private final Map<String,Object> param;

    public SqlStatement(JdbcTemplate jdbcTemplate, Map<String, Object> param) {
        this.jdbcTemplate = jdbcTemplate;
        this.param = param;
    }

    public SqlStatement(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.param = null;
    }

    public SqlStatement SELECT(String... columns){
        return this.append(" SELECT ")
                .appendArray(columns);
    }
    public SqlStatement SELECT_ALL(){
        return this.SELECT("*");
    }

    public SqlStatement FROM(String table, String alias ){
        this.append(" FROM ").append(table);
        if(!StringUtils.isEmpty(alias)){
            return this.append(" AS ").append(alias);
        }
        return this;
    }

    public SqlStatement TRUNCATE_TABLE(String table){
        return this.append(" TRUNCATE TABLE ").append(table);
    }

    public SqlStatement DELETE_FROM(String table){
        return this.append(" DELETE FROM ").append(table);
    }

    public SqlStatement FROM(SqlStatement sqlStatement, String alias){
        return this.OPEN()
                .relate(sqlStatement)
                .CLOSE().AS(alias);
    }
    public SqlStatement FROM(String table){
        return this.FROM(table,null);
    }

    public SqlStatement WHERE(String condition){
        return this.append(" WHERE ").append(condition);
    }
    public Condition WHERE(){
        return new Condition(this);
    }

    public SqlStatement AND(String condition){
        return this.append(" AND ").append(condition);
    }

    public Condition AND(){
        return new Condition(this,ConditionEnum.AND);
    }

    public SqlStatement OR(String condition){
        return this.append(" OR ").append(condition);
    }

    public Condition OR(){
        return new Condition(this,ConditionEnum.OR);
    }

    public SqlStatement ON(String condition){
        return this.append(" ON ").append( condition );
    }

    public Condition ON(){
        return new Condition(this,ConditionEnum.ON);
    }

    private SqlStatement ORDER_BY(Boolean asc, String... sorts){
        this.append(" ORDER BY ")
                .append(StringUtils.arrayToDelimitedString(sorts,","));
        if(asc == null){
            return this;
        }
        if( asc ){
            return this.append(" ASC ");
        }
        return this.append(" DESC ");
    }
    public SqlStatement ORDER_BY_DESC(String... sorts){
        return this.ORDER_BY(false,sorts);
    }
    public SqlStatement ORDER_BY_ASC(String... sorts){
        return this.ORDER_BY(true,sorts);
    }
    public SqlStatement LIMIT(String start,String end){
        return this.append(" LIMIT ").append(start).append(",")
                .append(end);
    }

    public SqlStatement ORDER_BY(String... sorts){
        return this.ORDER_BY(null,sorts);
    }

    public Condition LEFT_JOIN_ON(String table, String alias){
        return this.append(" LEFT JOIN ")
                .append(table).AS(alias).ON();
    }
    public Condition RIGHT_JOIN_ON(String table, String alias){
        return this.append(" RIGHT JOIN ")
                .append(table).AS(alias).ON();
    }

    public SqlStatement INNER_JOIN_ON(String table, String alias){
        this.append(" INNER JOIN ")
                .append(table).AS(alias).ON();
        return this;
    }

    public SqlStatement OPEN(){
        return this.append(" ( ");
    }
    public SqlStatement CLOSE(){
        return this.append(" ) ");
    }

    public SqlStatement AS(String alias){
        return this.append(" AS ").append(alias);
    }

    public SqlStatement IN(String... ins){
        return this.append(" IN (")
                .append( StringUtils.arrayToDelimitedString(ins,","))
                .append(" ) ");
    }

    public SqlStatement INSERT(String table, String... args){
        return this.append(" INSERT INTO ").append(table)
                .OPEN().appendArray(args).CLOSE();
    }

    public SqlStatement VALUES(List<String[]> entities){
        this.append(" VALUES ");
        for (int i = 0; i < entities.size(); i++) {
            if(i > 0){
                this.next();
            }
            this.OPEN().appendArray(entities.get(i)).CLOSE();
        }
        return this;
    }

    public SqlStatement ON_DUPLICATE_KEY_UPDATE(String... args){
        this.append(" ON DUPLICATE KEY UPDATE ");
        for (int i = 0; i < args.length; i++) {
            if( i > 0){
                this.next();
            }
            this.append(" ").append(args[i]).append(" = VALUES(").append(args[i]).append(")");
        }
        return this;
    }

    @Override
    public String toString(){
        return this.statement.toString();
    }

    public class Condition{
        private StringBuilder condition = new StringBuilder();
        private SqlStatement sqlStatement;
        private boolean first = true;
        private boolean stop = false;
        private ConditionEnum type;


        Condition(SqlStatement sqlStatement) {
            this.sqlStatement = sqlStatement;
            this.type = ConditionEnum.WHERE;
        }

        Condition(SqlStatement sqlStatement, ConditionEnum type) {
            this.sqlStatement = sqlStatement;
            this.type = type;
        }
        public Condition IF(boolean flag){
            this.stop = !flag;
            return this;
        }
        public Condition END_IF(){
            this.stop = false;
            return this;
        }

        public Condition OPEN(){
            return this.append(" ( ");
        }
        public Condition CLOSE(){
            return this.append(" ) ");
        }

        public Condition AS(String alias){
            return this.append(" AS ").append(alias);
        }

        public Condition IN(String property,String... ins){
            return this.append(" ").append(property)
                    .append(" IN (")
                    .append( StringUtils.arrayToDelimitedString(ins,","))
                    .append(" ) ").notFirst();
        }
        public Condition IN(String property, List<String>ins){
            return this.IN(property,ins.toArray(new String[0]));
        }

        public Condition AND(){
            if(!first){
                return this.append(" AND ").notFirst();
            }
            return this;
        }

        public Condition AND(String condition){
            return this.AND().append(condition);
        }

        public Condition OR(){
            if(!first){
                return this.append(" OR ").notFirst();
            }
            return this;
        }

        public Condition OR(String condition){
            return this.OR().append(condition);
        }

        public Condition EQUALSE(String property, String param){
            return this.append(" ").append(property).append(" = ").append(param).notFirst();
        }

        public Condition BIGGER_INCLUDE(String property, String param){
            return this.COMPARE(property,param,true,true);
        }

        public Condition BIGGER(String property, String param){
            return this.COMPARE(property,param,true,false);
        }
        public Condition SAMMER_INCLUDE(String property, String param){
            return this.COMPARE(property,param,false,true);
        }
        public Condition SAMMER(String property, String param){
            return this.COMPARE(property,param,false,false);
        }

        public Condition BETWEEN(String property, String min, String max){
            return this.append(" ").append(property).append(" BETWEEN ").append(min).append(" AND ").append(max).notFirst();
        }

        public Condition COMPARE(String property, String param, boolean bigger, boolean include){
            return this.append(" ").append(property).append(bigger? " >": " <").append(include? "= ":" ").append(param).notFirst();
        }

        public Condition LIKE(String property, String like){
            return this.append(property).append(" LIKE ").append(like).notFirst();
        }
        private Condition append(String append){
            if(stop){
                return this;
            }
            this.condition.append(append);
            return this;
        }

        private Condition notFirst(){
            if(stop){
                return this;
            }
            this.first = false;
            return this;
        }

        public SqlStatement END_Condition(){
            switch (type){
                case ON: return this.sqlStatement.ON( this.condition.toString() );
                case AND: return this.sqlStatement.AND( this.condition.toString() );
                case WHERE:
                    default: return this.sqlStatement.WHERE( this.condition.toString() );
            }
        }


    }



    @Data
    @Accessors(chain = true)
    class Param{
        private static final String PREPARED_PREFIX = "#";
        private static final String NORMAL_PREFIX = "$";

        private String name;
        private Boolean prepared;
        private String paramStr;

        Param(String paramStr) {
            this.name = NativeMySqlUtils.extractParamName(paramStr);
            this.prepared  = paramStr.startsWith(PREPARED_PREFIX);
            this.paramStr = (prepared ? PREPARED_PREFIX : NORMAL_PREFIX) + "{" + name + "}";
        }

        @Override
        public String toString() {
            return paramStr;
        }
    }

    public <T> List<T> query( Class<T> tClass){
        Object[] args = getArgsAndProcessStatement();
        return this.jdbcTemplate.query(this.statement.toString(),args,NativeMySqlUtils.createRowMapper(tClass));
    }

    public int modify(){
        Object[] args = getArgsAndProcessStatement();
        return this.jdbcTemplate.update(this.statement.toString(),args);
    }
    public Number insert(){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Object[] args = getArgsAndProcessStatement();
        this.jdbcTemplate.update(new SimplePreparedStatementCreator(this.statement.toString(),args),keyHolder);
        return keyHolder.getKey();
    }
    public void update(){
        Object[] args = getArgsAndProcessStatement();
        this.jdbcTemplate.update(this.statement.toString(),args);
    }

    private Object[] getArgsAndProcessStatement(){
        List<Param> prepared = toPreparedStatementSql(param);
        if(prepared == null){
            return null;
        }
        List<Object> objects = new ArrayList<>(prepared.size());
        prepared.forEach( prepare -> {
            Object arg = param.get(prepare.getName());
            if(arg instanceof Collection){
                objects.addAll(((Collection) arg));
            }else{
                objects.add(arg);
            }
        });
        return objects.toArray();
    }

    private List<Param> toPreparedStatementSql(Map<String,Object> objectMap){
        List<Param> params = extractParam();
            params.stream().filter(param -> !param.getPrepared()).forEach(param -> {
                String value = NativeMySqlUtils.toStringForSqlParam(objectMap.get(param.getName()));
                this.statement.replace(0,this.statement.length(),StringUtils.replace(this.statement.toString(), param.toString(), value));
                params.remove(param);
            });
            params.forEach(prepared -> this.statement.replace(0,this.statement.length(),StringUtils.replace(this.statement.toString(), prepared.toString(), "?")));
        return params;
    }

    private List<Param> extractParam(){
        List<String> paramString = NativeMySqlUtils.extractParamString(this.statement.toString());
        return paramString.parallelStream().map(Param::new).collect(Collectors.toList());
    }

    private SqlStatement relate(SqlStatement sqlStatement){
        this.statement.append(sqlStatement.statement);
        return this;
    }
    private SqlStatement append(String str){
        this.statement.append(str);
        return this;
    }
    private SqlStatement appendArray(Object... args){
        return this.append(StringUtils.arrayToDelimitedString(args,","));
    }
    private SqlStatement appendArray(String... args){
        return this.append(StringUtils.arrayToDelimitedString(args,","));
    }
    private SqlStatement next(){
        return this.append(",");
    }


    private static class SimplePreparedStatementCreator implements PreparedStatementCreator, SqlProvider {

        private final String sql;
        private PreparedStatementSetter setter;

        public SimplePreparedStatementCreator(String sql,Object[] args) {
            Assert.notNull(sql, "SQL must not be null");
            this.sql = sql;
            this.setter = new ArgumentPreparedStatementSetter(args);
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            PreparedStatement ps = con.prepareStatement(this.sql, Statement.RETURN_GENERATED_KEYS);
            this.setter.setValues(ps);
            return ps;
        }

        @Override
        public String getSql() {
            return this.sql;
        }
    }

    private enum ConditionEnum{
        WHERE,OR,AND,ON;
    }
}