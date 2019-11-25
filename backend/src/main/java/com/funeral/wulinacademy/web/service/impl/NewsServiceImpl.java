package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.model.NewsModify;
import com.funeral.wulinacademy.web.repository.NewsRepository;
import com.funeral.wulinacademy.web.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Implement Of NewsService
 *
 * @author FuneralObjects
 * @date 2019-03-29 14:40
 */
@Service
@Transactional(readOnly = true)
public class NewsServiceImpl extends BaseService implements NewsService {
    @Resource
    private NewsRepository newsRepository;

    @Override
    public String getName() {
        return "NEWS";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addNews(NewsModify modify, String uuid) {
        Assert.notNull(modify, "The modify of news cannot be null!");
        Assert.notNull(uuid, "The uuid cannot be null!");
        return newsRepository.save(new News()
                .setPreview(modify.getPreview())
                .setEventDate(modify.getEventDate())
                .setTitle(modify.getTitle())
                .setStatus(StandardStatus.INVISIBLE)
                .setThumbnail(modify.getThumbnail())
                .setUuid(uuid)).getNewsId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateNewsInfo(Integer newsId, NewsModify modify) {
        Assert.notNull(newsId, "The newsId cannot be null");
        Assert.notNull(modify, "The modify of news cannot be null!");
        News news = newsRepository.findById(newsId).orElseThrow( () -> new IllegalArgumentException("The newsId is invalid"));
        news.setPreview(modify.getPreview())
                .setEventDate(modify.getEventDate())
                .setTitle(modify.getTitle())
                .setThumbnail(modify.getThumbnail());
        newsRepository.save(news);
    }
}
