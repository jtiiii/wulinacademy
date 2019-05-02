package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * 新闻实体类
 *
 * @author FuneralObjects
 * @date 2019-01-06 01:41
 */
@Data
@Accessors(chain = true)
@Table("t_news")
public class News implements ParseId<Integer> {
    /**
     * 主键ID
     */
    @Id
    @Column("news_id")
    private Integer newsId;
    /**
     * 标题
     */
    @Column("title")
    private String title;
    /**
     * 事件日期
     */
    @Column("event_date")
    private Date eventDate;
    /**
     * 状态 ，VISIBLE - 可见 | INVISIBLE - 不可见 | DELETED - 删除
     */
    @Column("status")
    private Integer status;
    /**
     * 缩略图路径
     */
    @Column("thumbnail")
    private String thumbnail;
    /**
     * 预览信息
     */
    @Column("preview")
    private String preview;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public Integer parse(String id) {
        return Integer.valueOf(id);
    }
}
