package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:30
 */
@Data
@Accessors(chain = true)
@Table("v_folder")
public class Folder implements ParseId<Integer>{
    /**
     * 文件夹ID
     */
    @Id
    private Integer folderId;
    /**
     * 文件夹名称
     */
    private String folderName;
    /**
     * 父级ID
     */
    private Integer parentId;
    /**
     * 所属用户ID
     */
    private String userId;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新日期
     */
    private Date updateTime;
    /**
     * @see com.funeral.wulinacademy.web.common.standard.StatusStandard
     */
    private Integer status;

    @Override
    public Integer parse(String id) {
        return Integer.parseInt(id);
    }
}
