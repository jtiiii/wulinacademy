package com.funeral.wulinacademy.web.controller.model.image;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-15 15:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ImageModel extends ImageSaveModel{
    /**
     * 图片ID
     */
    private Integer id;
    /**
     * 创建事件
     */
    private Date createTime;
    /**
     * 后缀名
     */
    private String suffix;
}
