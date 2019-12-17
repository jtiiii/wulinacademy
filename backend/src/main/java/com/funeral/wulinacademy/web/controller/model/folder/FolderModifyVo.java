package com.funeral.wulinacademy.web.controller.model.folder;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-14 15:38
 */
@Data
@Accessors(chain = true)
public class FolderModifyVo {
    /**
     * 文件夹名称
     */
    @NotBlank
    private String name;
    /**
     * 父级ID
     */
    @NotNull
    private Integer parent;

}
