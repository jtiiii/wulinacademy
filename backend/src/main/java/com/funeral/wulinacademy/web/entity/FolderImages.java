package com.funeral.wulinacademy.web.entity;

import com.funeral.wulinacademy.web.entity.pk.FolderImagePk;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-14 14:18
 */
@Data
@Accessors(chain = true)
@Table(name = "t_folder_images")
@Entity
public class FolderImages{

    @EmbeddedId
    private FolderImagePk pk;

    /**
     * 图片ID
     */
    @Column(name = "sha1_md5")
    private String sha1Md5;
    /**
     * 后缀名
     */
    private String suffix;
}
