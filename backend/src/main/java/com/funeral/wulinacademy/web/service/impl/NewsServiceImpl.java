package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.common.standard.StatusStandard;
import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.entity.NewsContent;
import com.funeral.wulinacademy.web.repository.NewsContentRepository;
import com.funeral.wulinacademy.web.repository.NewsRepository;
import com.funeral.wulinacademy.web.service.NewsService;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Implement Of NewsService
 *
 * @author FuneralObjects
 * @date 2019-03-29 14:40
 */
@Service
public class NewsServiceImpl extends BaseService implements NewsService {
    @Resource
    private NewsRepository newsRepository;

    @Resource
    private NewsContentRepository newsContentRepository;

    @Override
    public Page<News> findByTitleAndVisibleForPage(String titleKeyWord, Pageable pageable, Date startTime){
        Set<Integer> statuses = new HashSet<>(1);
        statuses.add(StatusStandard.VISIBLE.getNum());
        long total = newsRepository.countNewsByTitleContainsAndStatus(startTime,titleKeyWord,statuses);
        return new PageImpl<>(
                total > 0
                        ? newsRepository.findNewsByTitleContainsAndStatusOrderByEventDateLimit(startTime,titleKeyWord,statuses,pageable.getOffset(),pageable.getPageSize())
                        : new ArrayList<>(0)
                ,pageable,total);
    }

    @Override
    public void saveOrUpdate(News news) throws ValidateException {
        if(news.getEventDate() == null){
            news.setEventDate(new Date());
        }
        ValidateException.validStatus(news.getStatus());
        newsRepository.save(news);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<News> saveBatch(Collection<News> saves) throws ValidateException{
        if(saves.parallelStream().anyMatch(news -> StatusStandard.getStatusByNum(news.getStatus()) == null)){
            throw new ValidateException("Illegal arg of status!");
        }
        Iterable<News> saved = newsRepository.saveAll(saves);
        List<News> result = new ArrayList<>(saves.size());
        saved.forEach(result::add);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer newsId) {
        newsRepository.findById(newsId).ifPresent( news -> {
            news.setStatus(StatusStandard.DELETED.getNum());
            newsRepository.save(news);
        });
    }

    @Override
    public void saveOrUpdateContent(NewsContent content) throws ValidateException {
        Assert.notNull(content,"param of content is null!");
        Optional<News> optional =  newsRepository.findById(content.getNewsId());
        if(optional.isPresent()){
            ValidateException.validStatusIsNotDelete(optional.get().getStatus());
            newsContentRepository.save(content);
        }
    }

    @Override
    public NewsContent getContentById(Integer newsId) {
        Optional<NewsContent> content = newsContentRepository.findById(newsId);
        return content.orElse(null);
    }

    @Override
    public String getName() {
        return "NEWS";
    }
}
