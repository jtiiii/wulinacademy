package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.entity.QNews;
import com.funeral.wulinacademy.web.model.NewsModify;
import com.funeral.wulinacademy.web.repository.NewsRepository;
import com.funeral.wulinacademy.web.service.NewsService;
import com.funeral.wulinacademy.web.util.CollectionUtils;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

/**
 * Implement Of NewsService
 *
 * @author FuneralObjects
 * CreateTime 2019-03-29 14:40
 */
@Service
@Transactional(readOnly = true)
public class NewsServiceImpl extends BaseService implements NewsService {
    @Resource
    private NewsRepository newsRepository;

    @Resource
    private JPQLQueryFactory jpqlQueryFactory;

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

    @Override
    public String getUuidById(Integer newsId) {
        Assert.notNull(newsId, "The newsId cannot be null");
        return newsRepository.findUuidByNewsId(newsId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteNews(Integer newsId) {
        newsRepository.updateStatusById(StandardStatus.DELETED, newsId);
    }

    @Override
    public Page<News> findPageByEventDateAndKeywords(Pageable pageable, String keywords, Date start, Date end, Set<StandardStatus> statuses) {
        JPQLQuery<News> query = jpqlQueryFactory.select(QNews.news)
                .from(QNews.news);
        if(!StringUtils.isEmpty(keywords)){
            query.where(QNews.news.title.like(keywords));
        }
        if(start != null){
            query.where(QNews.news.eventDate.after(start));
        }
        if(end != null){
            query.where(QNews.news.eventDate.before(end));
        }
        if(!CollectionUtils.isEmpty(statuses)){
            query.where(QNews.news.status.in(statuses));
        }else{
            query.where(QNews.news.status.ne(StandardStatus.DELETED));
        }
        QueryResults<News> queryResults = query.limit(pageable.getPageSize()).offset(pageable.getOffset()).fetchResults();
        if(queryResults.getTotal() == 0){
            return Page.empty(pageable);
        }
        return new PageImpl<>(queryResults.getResults(),pageable,queryResults.getTotal());
    }
}
