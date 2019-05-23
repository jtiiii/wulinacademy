package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.service.exception.ValidateException;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:45
 */
public interface FolderService {
    Integer ROOT_ID = 0;

    void saveOrUpdateRootFolder(Folder folder) throws ValidateException;

    boolean hasSon(Integer folderId);

    void deleteFolder(Integer folderId) throws ValidateException;

    List<Folder> findByParentId(Integer parentId) throws ValidateException;

    boolean belongUser(Integer folder,String user);

    List<Folder> findRootFolderByUserId(String userId);
}
