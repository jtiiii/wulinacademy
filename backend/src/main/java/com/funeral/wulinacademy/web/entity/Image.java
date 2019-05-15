package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:28
 */
@Data
@Accessors(chain = true)
@Table("v_image")
public class Image implements ParseId<Integer>{
    /**
     * 图片ID
     */
    @Id
    private Integer imageId;
    /**
     * 图片位置
     */
    private String site;
    /**
     * 图片MD5值
     */
    private String md5;
    /**
     * 状态
     * @see com.funeral.wulinacademy.web.common.standard.StatusStandard
     */
    private Integer status;
    private Date createTime;

    @Override
    public Integer parse(String id) {
        return Integer.parseInt(id);
    }
}
