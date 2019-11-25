package com.funeral.wulinacademy.web.entity.pk;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author FuneralObjects
 * Create date: 2019/11/22 9:56 AM
 */
@Data
@Accessors(chain = true)
@Embeddable
@EqualsAndHashCode
public class FolderImagePk implements Serializable {
    /**
     * 文件夹Id
     */
    private Integer folderId;
    /**
     * 图片名称
     */
    private String imageName;
}
