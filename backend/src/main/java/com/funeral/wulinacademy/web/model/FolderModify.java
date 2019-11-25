package com.funeral.wulinacademy.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * Create date: 2019/11/25 9:23 AM
 */
@Data
@Accessors(chain = true)
public class FolderModify {
    /**
     * 文件夹名称
     */
    private String folderName;
    /**
     * 父级ID
     */
    private Integer parentId;
}
