package com.funeral.wulinacademy.web.controller.model.news;

import com.funeral.wulinacademy.web.entity.NewsContent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author FuneralObjects
 * @date 2019-05-01 04:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class NewsContentModel extends NewsContentSaveModel {
    @NotNull
    private Integer newsId;

    public NewsContent toEntity(){
        return new NewsContent().setNewsId(newsId).setContent(getContent().getBytes(UTF_8));
    }

    public NewsContentModel(){}
    public NewsContentModel(NewsContent content){
        if(content == null){
            return;
        }
        this.newsId = content.getNewsId();
        setContent(new String(content.getContent(),UTF_8));
    }
    public NewsContentModel(Integer newsId, NewsContentSaveModel saveModel){
        this.newsId = newsId;
        setContent(saveModel.getContent());
    }
}
