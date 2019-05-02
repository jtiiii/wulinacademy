package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 新闻正文
 *
 * @author FuneralObjects
 * @date 2019-01-06 01:45
 */

@Data
@Accessors(chain = true)
@Table("t_news_content")
public class NewsContent implements ParseId<Integer>  {
    /**
     * News的主键ID
     */
    @Id
    private Integer newsId;
    /**
     * 正文内容
     */
    private byte[] content;

    @Override
    public Integer parse(String id) {
        return this.newsId;
    }
}
