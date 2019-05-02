package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.entity.ParseId;
import com.funeral.wulinacademy.web.repository.BaseRepository;
import com.funeral.wulinacademy.web.repository.sql.SqlStatement;
import com.funeral.wulinacademy.web.util.NativeMySqlUtils;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FuneralObjects
 * @date 2019-04-16 01:07
 */
public abstract class BaseRepositoryImpl<T extends ParseId<ID>,ID> implements BaseRepository<T,ID> {

    private JdbcTemplate jdbcTemplate;

    private String table;
    private String id;
    private Class<ID> idClass;
    private Class<T> tClass;
    private String[] properties;
    private String[] columns;
    private Map<String,String> propertyColumnMap;
    private Map<String,String> columnPropertyMap;

    @SuppressWarnings("unchecked")
    @PostConstruct
    private void init(){
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        this.tClass = (Class<T>) types[0];
        this.idClass = (Class<ID>) types[1];
        Table table = tClass.getAnnotation(Table.class);
        if(table != null){
            this.table = table.value();
        }
        Field[] fields = this.tClass.getDeclaredFields();
        this.properties = new String[fields.length];
        this.columns = new String[fields.length];
        this.propertyColumnMap = new HashMap<>(fields.length);
        this.columnPropertyMap = new HashMap<>(fields.length);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();
            properties[i] = name;
            Column column = field.getAnnotation(Column.class);
            if(column ==null){
                this.columns[i] = StringUtils.camelToUnderline(name);
            }else{
                this.columns[i] = column.value();
            }
            this.propertyColumnMap.put(this.properties[i],this.columns[i]);
            this.columnPropertyMap.put(this.columns[i],this.properties[i]);
            if(field.getAnnotation(Id.class) == null){
                continue;
            }
            this.id = name;
        }

    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> S save(S entity){
        Map<String,Object> params = getAllField(entity,true);

        int index = 0;
        boolean isUpdate = !StringUtils.isEmpty(this.id) && params.containsKey(this.id);
        String[] columns = new String[ params.size()];
        for (String s : params.keySet()) {
            columns[index] = propertyColumnMap.get(s);
            index++;
        }
        SqlStatement sqlStatement = new SqlStatement(jdbcTemplate,params).INSERT(table,columns);
        List<String[]> args = new ArrayList<>(1);
        String[] prepared = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            prepared[i] = NativeMySqlUtils.convertPropertyToParam(columnPropertyMap.get(columns[i]),true);
        }
        args.add(prepared);
        sqlStatement.VALUES(args).ON_DUPLICATE_KEY_UPDATE( columns );
        String id;
        if(!isUpdate){
            id = sqlStatement.insert().toString();
        }else{
            sqlStatement.update();
            id = String.valueOf(params.get(this.id));
        }
        Optional<T> find = findById(entity.parse(id));
        find.ifPresent(t -> BeanUtils.copyProperties(t, entity));
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return Streamable.of(entities).stream().map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(ID id) {
        Map<String,Object> params = new HashMap<>(1);
        params.put(this.id,id);
        List<T> list = new SqlStatement(this.jdbcTemplate,params)
                .SELECT_ALL()
                .FROM(this.table)
                .WHERE()
                .EQUALSE(this.propertyColumnMap.get(this.id),NativeMySqlUtils.convertPropertyToParam(this.id,true))
                .END_Condition()
                .query(tClass);
        if(list.size() > 0){
            return Optional.of(list.get(0));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<T> findAll() {
        return new SqlStatement(jdbcTemplate).SELECT_ALL().FROM(this.table).query(this.tClass);
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        Map<String,Object> params = new HashMap<>(16);
        ids.forEach( id -> params.put(String.valueOf(id.hashCode()),id));
        SqlStatement sqlStatement = new SqlStatement(jdbcTemplate,params)
                .SELECT_ALL()
                .FROM(this.table);
        SqlStatement.Condition condition = sqlStatement.WHERE().OPEN();
        int i = 0;
        for (String s : params.keySet()) {
            if( i > 0){
                condition.OR();
            }
            condition.EQUALSE(this.id,s);
            i++;
        }
        return condition.END_Condition().query(tClass);
    }

    @Override
    public long count() {
        return new SqlStatement(jdbcTemplate)
                .SELECT("COUNT(1)").AS("c").FROM(this.table).query(long.class).get(0);
    }

    @Override
    public void deleteById(ID id) {
        Map<String,Object> param = new HashMap<>(1);
        param.put(this.id,id);
        new SqlStatement(jdbcTemplate,param).DELETE_FROM(this.table)
                .WHERE().EQUALSE(propertyColumnMap.get(this.id),NativeMySqlUtils.convertPropertyToParam( this.id,true))
                .END_Condition().modify();
    }

    @Override
    public void delete(T entity) {
        Map<String,Object> params = getAllField(entity,true);
        SqlStatement.Condition condition =new SqlStatement(jdbcTemplate,params).DELETE_FROM(this.table).WHERE();
        params.keySet().forEach( key -> {
            String column = propertyColumnMap.get(key);
            condition.AND().EQUALSE(column,NativeMySqlUtils.convertPropertyToParam(key,true));
        });
        condition.END_Condition().modify();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(Iterable<? extends T> entities) {
        Streamable.of(entities).forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        new SqlStatement(jdbcTemplate).TRUNCATE_TABLE(this.table).modify();
    }

    @Override
    public <S extends T> void saveBatch(Iterable<S> entities) {
        List<Map<String,Object>> paramList = new ArrayList<>();
        Streamable.of(entities).stream().forEach( entity -> paramList.add(getAllField(entity,false)));
        Map<String,Object> params = new HashMap<>(paramList.size() * properties.length);
        List<String[]> argStr = new ArrayList<>(paramList.size());
        for (int i = 0; i < paramList.size(); i++) {
            Map<String,Object> param = paramList.get(i);
            String[] entityArg = new String[properties.length];
            for (int j = 0; j < properties.length; j++) {
                Object arg = param.get(properties[j]);
                String name = properties[j]+"_"+i;
                entityArg[j] = NativeMySqlUtils.convertPropertyToParam(name,true);
                params.put(name,arg);
            }
            argStr.add(entityArg);
        }
        List<String> updateColumns = new ArrayList<>(properties.length - 1);
        for (String property : properties) {
            if(id.equals(property)){
                continue;
            }
            updateColumns.add(propertyColumnMap.get(property));
        }
        new SqlStatement(this.jdbcTemplate,params).INSERT(this.table,columns).VALUES(argStr).ON_DUPLICATE_KEY_UPDATE( updateColumns.toArray( new String[0]) )
        .modify();
    }

    JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    String getTable(){
        return table;
    }
    String getId(){
        return id;
    }

    private Map<String,Object> getAllField(T object, boolean ignoreNull){
        Map<String,Object> result = new HashMap<>(properties.length);
        try {
            for (String property : properties) {
                Object arg = tClass.getMethod(StringUtils.getter(property)).invoke(object);
                if(arg == null && ignoreNull){
                    continue;
                }
                result.put(property,arg);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return result;
    }



}
