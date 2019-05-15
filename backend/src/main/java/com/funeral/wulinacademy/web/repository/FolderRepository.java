package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.Folder;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:44
 */
public interface FolderRepository extends BaseRepository<Folder,Integer> {

    List<Folder> findByParentId(Integer parentId);

    boolean existsFolderByParentId(Integer parentId);
}
