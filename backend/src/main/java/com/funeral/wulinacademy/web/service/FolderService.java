package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.service.exception.BusinessException;
import com.funeral.wulinacademy.web.service.exception.ValidateException;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:45
 */
public interface FolderService {

    void saveOrUpdateRootFolder(Folder folder) throws ValidateException;

    boolean hasSon(Integer folderId);

    void deleteFolder(Integer folderId) throws ValidateException;
}
