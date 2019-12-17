package com.funeral.wulinacademy.web.controller.model.image;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-15 15:29
 */
@Data
@Accessors(chain = true)
public class ImageVo {
    /**
     * 图片名称
     */
    private String name;
    /**
     * 图片ID
     */
    private String sha1Md5;
    /**
     * 后缀名
     */
    private String suffix;
}
