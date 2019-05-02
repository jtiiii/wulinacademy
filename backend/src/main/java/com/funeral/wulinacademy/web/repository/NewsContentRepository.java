package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.NewsContent;
import com.funeral.wulinacademy.web.repository.impl.BaseRepositoryImpl;
import org.springframework.data.repository.CrudRepository;

/**
 * @author FuneralObjects
 * @date 2019-01-11 22:30
 */
public interface NewsContentRepository extends BaseRepository<NewsContent,Integer> {
}
