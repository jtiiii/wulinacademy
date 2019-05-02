package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.News;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author FuneralObjects
 * @date 2019-01-06 02:22
 */
public interface NewsRepository extends BaseRepository<News,Integer> {

    /**
     * 通过标题关键字有限查询符合状态的News
     * @param startTime 发布日期开始时间（可选）
     * @param search 查询关键字（可选）
     * @param statuses 状态值
     * @param start 开始位置
     * @param size 查询最大数量
     * @return News实体
     */
    List<News> findNewsByTitleContainsAndStatusOrderByEventDateLimit(Date startTime, String search, Set<Integer> statuses, long start, int size );

    /**
     * 作为 findNewsByTitleContainsAndStatusOrderByEventDateLimit 的count配合使用
     * @param startTime 发布日期开始时间（可选）
     * @param search 查询关键字（可选）
     * @param statuses 状态值
     * @return 总符合条件个数
     */
    long countNewsByTitleContainsAndStatus(Date startTime, String search, Set<Integer> statuses);
}
