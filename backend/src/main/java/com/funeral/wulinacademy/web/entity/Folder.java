package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:30
 */
@Data
@Accessors(chain = true)
@Table(name = "t_folder")
@Entity
@DynamicInsert
@DynamicUpdate
public class Folder{
    /**
     * 文件夹ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新日期
     */
    private Date updateTime;
}
