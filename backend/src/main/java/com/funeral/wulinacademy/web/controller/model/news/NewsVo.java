package com.funeral.wulinacademy.web.controller.model.news;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-01-11 22:49
 */
@Data
@Accessors(chain = true)
public class NewsVo {
    /**
     * 新闻主键
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 缩略图
     */
    private String thumbnail;
    /**
     * 事件日期
     */
    private Date eventDate;
    /**
     * 新闻预览
     */
    private String preview;
    /**
     * 是否启用
     */
    private Boolean enable;
}
