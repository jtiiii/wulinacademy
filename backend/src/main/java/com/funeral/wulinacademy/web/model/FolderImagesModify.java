package com.funeral.wulinacademy.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * Create date: 2019/11/22 5:47 PM
 */
@Data
@Accessors(chain = true)
public class FolderImagesModify {
    /**
     * 文件夹id
     */
    private Integer folderId;
    /**
     * 图片名
     */
    private String imageName;

    /**
     * 图片ID
     */
    private String sha1Md5;
    /**
     * 后缀名
     */
    private String suffix;
}
