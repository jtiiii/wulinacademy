package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.News;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author FuneralObjects
 * @date 2019-01-06 02:22
 */
public interface NewsRepository extends CrudRepository<News,Integer> {

    /**
     * 通过标题关键字有限查询符合状态的News
     * @param search 查询关键字
     * @param statuses 状态值
     * @param start 开始位置
     * @param size 数量限制
     * @return News实体
     */
    @Query("SELECT * FROM t_news WHERE title LIKE CONCAT('%', :search, '%') AND status IN ( :statuses ) ORDER BY event_date DESC LIMIT :start, :size ")
    List<News> findNewsByTitleContainsAndStatusOrderByEventDateLimit(String search, Set<String> statuses, int start, int size );

    /**
     * 作为 findNewsByTitleContainsAndStatusOrderByEventDateLimit 的count配合使用
     * @param search 查询关键字
     * @param statuses 状态值
     * @return 总符合条件个数
     */
    @Query("SELECT COUNT(1) FROM t_news WHERE title LIKE CONCAT('%', :search, '%') AND status IN ( :statuses )")
    Long countNewsByTitleContainsAndStatus(String search, Set<String> statuses);

    /**
     * 有限查询所有符合状态值的News
     * @param statuses 状态值
     * @param start 开始位置
     * @param size 数量限制
     * @return News实体
     */
    @Query("SELECT * from t_news WHERE status IN ( :statuses ) ORDER BY event_date DESC LIMIT :start, :size")
    List<News> findAllByStatusOrderByEventDateLimit(Set<String> statuses, int start, int size);


    /**
     * 查询所有符合状态值的News数量
     * @param statuses 状态值
     * @return 符合条件个数
     */
    @Query("SELECT COUNT(1) FROM t_news WHERE status IN ( :statuses )")
    Long countAllByStatus(Set<String> statuses);
}
