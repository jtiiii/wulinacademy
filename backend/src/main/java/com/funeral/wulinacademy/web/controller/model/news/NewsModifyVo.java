package com.funeral.wulinacademy.web.controller.model.news;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-04-27 16:21
 */
@Data
@Accessors(chain = true)
public class NewsModifyVo {
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
     * @see StandardStatus
     */
    private StandardStatus status;
    /**
     * 预览信息
     */
    private String preview;
    /**
     * 缩略图
     */
    private String thumbnail;
}
