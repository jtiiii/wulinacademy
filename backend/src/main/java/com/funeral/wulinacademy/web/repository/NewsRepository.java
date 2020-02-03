package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import com.funeral.wulinacademy.web.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author FuneralObjects
 * CreateTime 2019-01-06 02:22
 */
public interface NewsRepository extends JpaRepository<News,Integer> {

    @Query("SELECT uuid FROM News WHERE newsId = :newsId")
    String findUuidByNewsId(Integer newsId);

    @Modifying
    @Query("UPDATE News SET status = :status WHERE newsId = :newsId ")
    void updateStatusById(StandardStatus status, Integer newsId);

    @Modifying
    @Query("UPDATE News SET status = :status WHERE newsId = :newsId And status <> :withoutStatus ")
    void updateStatusByIdAndWithout(StandardStatus status, Integer newsId, StandardStatus withoutStatus);
}
