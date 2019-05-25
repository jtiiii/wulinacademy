package com.funeral.wulinacademy.web.controller.model.news;

import com.funeral.wulinacademy.web.entity.News;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author FuneralObjects
 * @date 2019-01-11 22:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class NewsModel extends NewsSaveModel {
    /**
     * 新闻主键
     */
    @NotNull
    private Integer id;

    public NewsModel(){}
    public NewsModel(News news) {
        this.id = news.getNewsId();
        setTitle(news.getTitle());
        setEventDate(news.getEventDate());
        setStatus(news.getStatus());
        setPreview(news.getPreview());
        setThumbnail(news.getThumbnail());
    }

    public NewsModel(Integer newsId,NewsSaveModel newsSaveModel){
        this.id = newsId;
        setTitle(newsSaveModel.getTitle());
        setEventDate(newsSaveModel.getEventDate());
        setStatus(newsSaveModel.getStatus());
        setPreview(newsSaveModel.getPreview());
        setThumbnail(newsSaveModel.getThumbnail());
    }

    @Override
    public News toEntity(){
        return super.toEntity().setNewsId(id);
    }

}
