package com.funeral.wulinacademy.web.entity;

import java.util.Date;

/**
 * 新闻实体类
 *
 * @author FuneralObjects
 * CreateTime 2019-01-06 01:41
 */
public class News {
    /**
     * 主键ID
     */
    private Integer newsId;
    /**
     * 标题
     */
    private String title;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新日期
     */
    private Long updateTIme;
    /**
     * 状态 ，VISIBLE - 可见 | INVISIBLE - 不可见 | DELETE - 删除
     */
    private String status;

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateTIme() {
        return updateTIme;
    }

    public void setUpdateTIme(Long updateTIme) {
        this.updateTIme = updateTIme;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
