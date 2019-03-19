package com.funeral.wulinacademy.web.controller.model;

import com.funeral.wulinacademy.web.entity.News;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-01-11 22:49
 */
@Setter
@Getter
public class NewsModel {
    /**
     * 新闻主键
     */
    private Integer newsId;
    /**
     * 标题
     */
    private String title;
    /**
     * 事件日期
     */
    private Date eventDate;
    /**
     * 状态
     */
    private String status;

    public NewsModel(){}
    public NewsModel(News news) {
        this.newsId = news.getNewsId();
        this.title = news.getTitle();
        this.eventDate = news.getEventDate();
        this.status = news.getStatus().toString();
    }

}
