package com.funeral.wulinacademy.web.controller.model.folder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-14 17:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class FolderModel extends FolderSaveModel {
    /**
     * 文件夹ID
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 文件路径
     */
    private String path;
}
