package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author FuneralObjects
 * @date 2019-01-11 23:00
 */
@Service
public class NewsService {

    @Resource
    private NewsRepository newsRepository;

    public Page<News> findByTitleAndVisibleForPage(String titleKeyWord, Pageable pageable){
        boolean noKeyword = StringUtils.isEmpty(titleKeyWord);
        Set<String> statuses = new HashSet<>();
        statuses.add(News.Status.VISIBLE.toString());
        int size = pageable.getPageSize();
        int start = new Long(pageable.getOffset()).intValue();
        List<News> newsList;
        long total;

        if(noKeyword){
            newsList = newsRepository.findAllByStatusOrderByEventDateLimit(statuses, start, size);
            total = newsRepository.countAllByStatus(statuses);
        }else{
            newsList = newsRepository.findNewsByTitleContainsAndStatusOrderByEventDateLimit(titleKeyWord.trim(),statuses,start,size);
            total = newsRepository.countNewsByTitleContainsAndStatus(titleKeyWord.trim(),statuses);
        }

        return new PageImpl<>(newsList,pageable,total);

    }

    @Transactional(rollbackFor = {Exception.class})
    public Integer save(String title, Date eventDate, News.Status status){
        News forSave = new News();
        forSave.setTitle(title);
        forSave.setEventDate(eventDate == null? new Date(): eventDate);
        forSave.setStatus(status == null? News.Status.VISIBLE: status);
        newsRepository.save(forSave);
        return forSave.getNewsId();
    }
}
