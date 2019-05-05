package com.funeral.wulinacademy.web.controller.model.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.funeral.wulinacademy.web.common.standard.DateStandard;
import com.funeral.wulinacademy.web.entity.News;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-04-27 16:21
 */
@Data
@Accessors(chain = true)
public class NewsSaveModel {
    /**
     * 标题
     */
    @NotBlank
    private String title;
    /**
     * 事件日期
     */
    @NotNull
    @JsonFormat(pattern = DateStandard.DATE_TIME_FORMAT,timezone = DateStandard.BEIJING_TIMEZONE)
    private Date eventDate;
    /**
     * 状态
     * @see com.funeral.wulinacademy.web.common.standard.StatusStandard
     */
    @NotNull
    private Integer status;
    /**
     * 预览信息
     */
    private String preview;
    /**
     * 缩略图
     */
    private String thumbnail;

    public News toEntity(){
        return new News()
                .setTitle(title)
                .setStatus(status)
                .setEventDate(eventDate)
                .setPreview(preview)
                .setThumbnail(thumbnail);
    }
}
