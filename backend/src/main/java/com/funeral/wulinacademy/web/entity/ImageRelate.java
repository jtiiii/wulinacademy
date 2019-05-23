package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-18 15:12
 */
@Data
@Accessors(chain =true)
@Table("v_image_relate")
public class ImageRelate implements ParseId<String> {
    private Integer imageId;
    private Integer folderId;
    private String imageName;
    private String suffix;
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
    public String parse(String id) {
        return id;
    }
}
