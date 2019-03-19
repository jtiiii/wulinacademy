package com.funeral.wulinacademy.web.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 新闻实体类
 *
 * @author FuneralObjects
 * @date 2019-01-06 01:41
 */
@Setter
@Getter
public class News {
    /**
     * 主键ID
     */
    @Id
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
     * 状态 ，VISIBLE - 可见 | INVISIBLE - 不可见 | DELETED - 删除
     */
    private Status status;

    public enum Status{
        /**
         * 可见
         */
        VISIBLE,
        /**
         * 不可见
         */
        INVISIBLE,
        /**
         * 删除的
         */
        DELETED
    }
}
