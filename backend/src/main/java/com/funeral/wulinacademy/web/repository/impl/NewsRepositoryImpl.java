package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.repository.NewsRepository;
import com.funeral.wulinacademy.web.repository.sql.SqlStatement;
import com.funeral.wulinacademy.web.util.CollectionUtils;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author FuneralObjects
 * @date 2019-04-14 11:07
 */
@Repository
public class NewsRepositoryImpl extends BaseRepositoryImpl<News,Integer> implements NewsRepository {

    @Override
    public List<News> findNewsByTitleContainsAndStatusOrderByEventDateLimit(Date startTime,String search, Set<Integer> statuses, long start, int size) {
        Map<String,Object> params = new HashMap<>(4);
        params.put("startTime",startTime);
        params.put("search",search);
        params.put("status",statuses);
        params.put("start",start);
        params.put("size",size);
        return new SqlStatement(getJdbcTemplate(),params)
                .SELECT( "*")
                .FROM(getTable())
                .WHERE()
                    .IF(startTime != null)
                        .AND().BIGGER_INCLUDE("event_date","#{startTime}").END_IF()
                    .IF(!StringUtils.isEmpty(search))
                        .AND().LIKE("title","CONCAT('%', #{search} , '%')").END_IF()
                    .IF(!CollectionUtils.isEmptyAfterRemoveNull(statuses))
                        .AND().IN( "status","#{status}" ).END_IF()
                .END_Condition()
                .ORDER_BY_DESC("event_date")
                .LIMIT("#{start}","#{size}").query(News.class);
    }

    @Override
    public long countNewsByTitleContainsAndStatus(Date startTime, String search, Set<Integer> statuses) {
        Map<String,Object> params = new HashMap<>(4);
        params.put("startTime",startTime);
        params.put("search",search);
        params.put("status",statuses);
        return new SqlStatement(getJdbcTemplate(),params)
                .SELECT( "COUNT(1)")
                .FROM(getTable())
                .WHERE()
                .IF(startTime != null)
                    .AND().BIGGER_INCLUDE("event_date","#{startTime}").END_IF()
                .IF(!StringUtils.isEmpty(search))
                    .AND().LIKE("title","CONCAT('%', #{search} , '%')").END_IF()
                .IF(!CollectionUtils.isEmptyAfterRemoveNull(statuses))
                    .AND().IN( "status","#{status}" ).END_IF()
                .END_Condition().query(long.class).get(0);
    }


}
