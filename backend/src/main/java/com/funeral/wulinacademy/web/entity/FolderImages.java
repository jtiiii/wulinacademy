package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:18
 */
@Data
@Accessors(chain = true)
@Table("t_folder_images")
public class FolderImages implements ParseId<Integer> {
    /**
     * 文件夹ID
     */
    @Id
    private Integer folderId;
    /**
     * 图片ID
     */
    private Integer imageId;

    @Override
    public Integer parse(String id) {
        return Integer.parseInt(id);
    }
}
