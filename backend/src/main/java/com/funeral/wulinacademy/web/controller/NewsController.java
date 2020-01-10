package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import com.funeral.wulinacademy.web.controller.model.news.NewsModifyVo;
import com.funeral.wulinacademy.web.controller.model.news.NewsVo;
import com.funeral.wulinacademy.web.model.NewsModify;
import com.funeral.wulinacademy.web.service.NewsService;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * 新闻内容controller
 *
 * @author FuneralObjects
 * CreateTime 2019-01-11 22:32
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @Value("${location.news}")
    private String dir;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @PostConstruct
    private void init(){
        File newsDir = new File(dir);
        if(!newsDir.exists()){
            newsDir.mkdirs();
        }
    }

    @GetMapping("/page/{num}-{size}")
    public Page<NewsVo> findPage(@PathVariable Integer num,
                                 @PathVariable Integer size,
                                 @RequestParam(value = "start",required = false) Long startTime,
                                 @RequestParam(value = "end",required = false) Long endTime,
                                 @RequestParam(value = "keywords",required = false) String keywords,
                                 @RequestParam(value = "status",required = false) Integer status){
        Pageable pageable = PageRequest.of(num,size);
        return newsService.findPageByEventDateAndKeywords(
                pageable,
                keywords == null? null: StringUtils.processFuzzyFullMatch(keywords),
                startTime == null? null: new Date(startTime),
                endTime == null? null: new Date(endTime),
                StandardStatus.of(status))
                .map( entity -> new NewsVo().setId(entity.getNewsId())
                .setEventDate(entity.getEventDate())
                .setPreview(entity.getPreview())
                .setUuid(entity.getUuid())
                .setThumbnail(entity.getThumbnail())
                .setTitle(entity.getTitle())
                .setEnable(StandardStatus.VISIBLE.equals(entity.getStatus())));
    }

    @PostMapping
    public Integer saveNews(@RequestBody @Valid NewsModifyVo model) {
        NewsModify modify = new NewsModify()
                .setEventDate(model.getEventDate())
                .setPreview(model.getPreview())
                .setThumbnail(model.getThumbnail())
                .setTitle(model.getTitle());
        String uuid = generateUuid();
        createContentFile(uuid);
        return newsService.addNews(modify,uuid);
    }

    @PutMapping("/{id}")
    public void updateNewsInfo(@PathVariable Integer id,
                               @RequestBody @Valid NewsModifyVo model){
        NewsModify modify = new NewsModify()
                .setEventDate(model.getEventDate())
                .setPreview(model.getPreview())
                .setThumbnail(model.getThumbnail())
                .setTitle(model.getTitle());
        newsService.updateNewsInfo(id,modify);
    }

    @PutMapping("/{id}/content")
    public void updateContent(@PathVariable Integer id,
                              @RequestBody String content){
        String uuid = newsService.getUuidById(id);
        if(content == null){
            content = "";
        }
        try {
            FileCopyUtils.copy(content.getBytes(StandardCharsets.UTF_8),getContentFile(uuid));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generateUuid(){
        return UUID.randomUUID().toString();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createContentFile(String uuid){
        File newsContent = getContentFile(uuid);
        if(!newsContent.exists()){
            try {
                newsContent.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private File getContentFile(String uuid){
        return new File(dir + uuid + ".html");
    }

}
