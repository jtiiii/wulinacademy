package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.model.FolderImagesModify;

/**
 * @author FuneralObjects
 * @date 2019-05-15 14:53
 */
public interface ImageService {
    void addImage(FolderImagesModify modify);

    void delete(Integer folderId, String name);

}
