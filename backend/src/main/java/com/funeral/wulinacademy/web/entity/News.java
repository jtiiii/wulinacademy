package com.funeral.wulinacademy.web.entity;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import com.funeral.wulinacademy.web.converter.StandardStatusConverter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 新闻实体类
 *
 * @author FuneralObjects
 * CreateTime 2019-01-06 01:41
 */
@Data
@Accessors(chain = true)
@Table(name = "t_news")
@Entity
@DynamicInsert
@DynamicUpdate
public class News {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Convert(converter = StandardStatusConverter.class)
    private StandardStatus status;
    /**
     * 缩略图路径
     */
    private String thumbnail;
    /**
     * 预览信息
     */
    private String preview;
    /**
     * 文件uuid
     */
    private String uuid;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
