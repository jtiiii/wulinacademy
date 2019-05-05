package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.entity.NewsContent;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-01-11 23:00
 */
public interface NewsService extends Service {

    Page<News> findByTitleAndVisibleForPage(String titleKeyWord, Pageable pageable,Date startTime);
    void saveOrUpdate(News news) throws ValidateException;
    List<News> saveBatch(Collection<News> saves) throws ValidateException;
    void delete(Integer newsId);
    void saveOrUpdateContent(NewsContent content) throws ValidateException;
    NewsContent getContentById(Integer newsId);
}
