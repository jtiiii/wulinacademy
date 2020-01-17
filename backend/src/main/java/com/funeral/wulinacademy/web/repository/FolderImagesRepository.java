package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.entity.pk.FolderImagePk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-14 14:20
 */
public interface FolderImagesRepository extends JpaRepository<FolderImages, FolderImagePk> {

    List<FolderImages> findAllByPk_FolderId(Integer folderId);

    @Modifying
    @Query("DELETE FROM FolderImages WHERE pk.folderId = :folderId ")
    void deleteAllByPk_FolderId(Integer folderId);

}
