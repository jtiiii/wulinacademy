package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:44
 */
public interface FolderRepository extends JpaRepository<Folder,Integer> {

    boolean existsAllByParentId(Integer parentId);

    List<Folder> findAllByParentId(Integer parentId);

    boolean existsAllByAndFolderName(String folderName);

    boolean existsAllByFolderIdNotAndFolderName(Integer folderId, String folderName);

    @Modifying
    @Query("UPDATE Folder SET folderName = :folderName WHERE folderId = :folderId")
    void updateFolderNameByFolderId(String folderName,Integer folderId);


}
