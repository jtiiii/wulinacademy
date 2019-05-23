package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.entity.Image;
import com.funeral.wulinacademy.web.entity.ImageRelate;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:20
 */
public interface FolderImagesRepository extends BaseRepository<FolderImages,Integer> {

    FolderImages save(Integer folderId, Integer imageId, String imageName);

}
