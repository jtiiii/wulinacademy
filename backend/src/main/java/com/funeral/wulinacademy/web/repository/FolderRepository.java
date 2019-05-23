package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.Folder;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:44
 */
public interface FolderRepository extends BaseRepository<Folder,Integer> {

    List<Folder> findAllByParentId(Integer parentId);

    boolean existsFolderByParentId(Integer parentId);

    boolean existsByFolderIdAndUserId(Integer folderId, String userId);

    List<Folder> findFoldersByParentIdAndUserId(Integer parentId,String userId);

}
