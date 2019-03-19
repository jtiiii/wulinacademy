package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.controller.model.NewsModel;
import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新闻内容controller
 *
 * @author FuneralObjects
 * @date 2019-01-11 22:32
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @GetMapping("/page-{page}-{size}")
    public Page<NewsModel> findByPage(@PathVariable("page") Integer page,
                                      @PathVariable("size") Integer size){
        Page<News> pNews = newsService.findByTitleAndVisibleForPage(null,PageRequest.of(page,size));
        List<NewsModel> newsModels = transformToModel(pNews.get().collect(Collectors.toList()));
        return new PageImpl<>(newsModels,pNews.getPageable(),pNews.getTotalElements());
    }

    @PostMapping
    public void saveNews(@RequestBody NewsModel newsModel){

    }

    private List<NewsModel> transformToModel(List<News> news){
        if(CollectionUtils.isEmpty(news)){
            return new ArrayList<>();
        }
        List<NewsModel> result = new ArrayList<>(news.size());
        news.forEach( value -> result.add(new NewsModel(value)));
        return result;
    }

}
