package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.model.NewsModify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * @author FuneralObjects
 * CreateTime 2019-01-11 23:00
 */
public interface NewsService extends Service {

    Integer addNews(NewsModify modify, String uuid);

    void updateNewsInfo(Integer newsId, NewsModify modify);

    String getUuidById(Integer newsId);

    Page<News> findPageByEventDateAndKeywords(Pageable pageable, String keywords, Date start, Date end, StandardStatus status);
}
