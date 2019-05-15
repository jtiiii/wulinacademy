package com.funeral.wulinacademy.web.controller.model.folder;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author FuneralObjects
 * @date 2019-05-14 15:38
 */
@Data
@Accessors(chain = true)
public class FolderSaveModel {
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
    /**
     * 所属用户ID
     */
    private String user;
    /**
     * @see com.funeral.wulinacademy.web.common.standard.StatusStandard
     */
    private Integer status;

}
