package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.model.FolderModify;

import java.util.List;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-14 14:45
 */
public interface FolderService {
    Integer ROOT_ID = 0;

    Integer addFolder(FolderModify modify);

    void updateFolderName(Integer folderId, String name);

    boolean hasSon(Integer folderId);

    void deleteFolder(Integer folderId);

    List<Folder> findByParentId(Integer parentId);

}
