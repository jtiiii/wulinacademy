package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author FuneralObjects
 * @date 2019-01-06 02:22
 */
public interface NewsRepository extends JpaRepository<News,Integer> {

    @Query("SELECT uuid FROM News WHERE newsId = :newsId")
    String findUuidByNewsId(Integer newsId);
}
