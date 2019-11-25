package com.funeral.wulinacademy.web.controller.model.folder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-14 17:20
 */
@Data
@Accessors(chain = true)
public class FolderVo {
    /**
     * 文件夹ID
     */
    private Integer id;
    /**
     * 文件夹名称
     */
    private String name;
    /**
     * 父级ID
     */
    private Integer parent;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
//    /**
//     * 文件路径
//     */
//    private String path;
}
