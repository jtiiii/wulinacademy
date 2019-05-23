package com.funeral.wulinacademy.web.controller.model.image;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * @date 2019-05-15 15:29
 */
@Data
@Accessors(chain = true)
public class ImageSaveModel {
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
    /**
     * 图片名称
     */
    private String name;
}
