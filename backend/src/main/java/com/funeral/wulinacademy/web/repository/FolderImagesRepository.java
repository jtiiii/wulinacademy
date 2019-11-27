package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.entity.pk.FolderImagePk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:20
 */
public interface FolderImagesRepository extends JpaRepository<FolderImages, FolderImagePk> {

    List<FolderImages> findAllByPk_FolderId(Integer folderId);

}
