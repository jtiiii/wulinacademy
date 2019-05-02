package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.common.standard.DateStandard;
import com.funeral.wulinacademy.web.controller.annotation.AutoValid;
import com.funeral.wulinacademy.web.controller.exception.BadRequestException;
import com.funeral.wulinacademy.web.controller.exception.InternalServerErrorException;
import com.funeral.wulinacademy.web.controller.model.news.NewsContentModel;
import com.funeral.wulinacademy.web.controller.model.news.NewsContentSaveModel;
import com.funeral.wulinacademy.web.controller.model.news.NewsModel;
import com.funeral.wulinacademy.web.controller.model.news.NewsSaveModel;
import com.funeral.wulinacademy.web.entity.News;
import com.funeral.wulinacademy.web.entity.NewsContent;
import com.funeral.wulinacademy.web.service.NewsService;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
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

    @GetMapping("/page-{number}-{size}")
    public Page<NewsModel> findByPage(@PathVariable("number") Integer number,
                                      @PathVariable("size") Integer size,
                                      @RequestParam(value = "eventTime",required = false) String eventTime,
                                      @RequestParam(value = "search",required = false) String search) throws BadRequestException, InternalServerErrorException {
        Date start = null;
        try {
            if(!StringUtils.isEmpty(eventTime)){
                start = DateStandard.parseByTimestamp(eventTime);
            }
        } catch (ParseException e) {
            throw new BadRequestException("Date parse error: [eventTime] ",e);
        }
        Page<News> pNews = newsService.findByTitleAndVisibleForPage(search,PageRequest.of(number,size),start);
        return new PageImpl<>(
                pNews.get().map(NewsModel::new).collect(Collectors.toList()),
                pNews.getPageable(),
                pNews.getTotalElements());
    }

    @AutoValid
    @PostMapping
    public Integer saveNews(@RequestBody @Valid NewsSaveModel model, BindingResult br) throws ValidateException {
        News news = model.toEntity();
        newsService.saveOrUpdate(news);
        return news.getNewsId();
    }

    @AutoValid
    @PutMapping("/{id}")
    public void updateNews(@PathVariable Integer id, @RequestBody @Valid NewsSaveModel model, BindingResult br) throws ValidateException {
        NewsModel nm = new NewsModel(id,model);
        newsService.saveOrUpdate(nm.toEntity());
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Integer id){
        newsService.delete(id);
    }

    @GetMapping("/{id}/content")
    public NewsContentModel findContentById(@PathVariable("id") Integer newsId){
        return new NewsContentModel(newsService.getContentById(newsId));
    }

    @AutoValid
    @PostMapping("/{id}/content")
    public void saveContent(@PathVariable("id") Integer newsId, @RequestBody @Valid NewsContentSaveModel model, BindingResult br) throws ValidateException {
        newsService.saveOrUpdateContent(new NewsContentModel(newsId,model).toEntity());
    }

//    private List<NewsModel> transformToModel(List<News> news){
//        if(CollectionUtils.isEmptyAfterRemoveNull(news)){
//            return new ArrayList<>();
//        }
//        List<NewsModel> result = new ArrayList<>(news.size());
//        news.forEach( value -> result.add(new NewsModel(value)));
//        return result;
//    }

}
