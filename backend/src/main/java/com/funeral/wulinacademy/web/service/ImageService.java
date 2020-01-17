package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.model.FolderImagesModify;

import java.util.List;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-15 14:53
 */
public interface ImageService {
    void addImage(FolderImagesModify modify);

    void delete(Integer folderId, String name);

    void deleteAllByFolderId(Integer folderId);

    List<FolderImages> findAllByFolderId(Integer folderId);
}
