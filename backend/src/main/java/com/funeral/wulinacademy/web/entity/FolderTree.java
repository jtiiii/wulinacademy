package com.funeral.wulinacademy.web.entity;

import com.funeral.wulinacademy.web.converter.FolderPathConverter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-14 13:36
 */
@Data
@Accessors(chain = true)
@Table(name = "t_folder_tree")
@Entity
public class FolderTree{
    /**
     * 文件夹ID
     */
    @Id
    private Integer folderId;
    /**
     * 文件夹路径
     */

    @Convert(converter = FolderPathConverter.class)
    private List<Integer> path;

}
