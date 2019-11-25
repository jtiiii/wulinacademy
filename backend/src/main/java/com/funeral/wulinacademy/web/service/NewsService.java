package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.model.NewsModify;

/**
 * @author FuneralObjects
 * @date 2019-01-11 23:00
 */
public interface NewsService extends Service {

    Integer addNews(NewsModify modify, String uuid);

    void updateNewsInfo(Integer newsId, NewsModify modify);
}
