package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.entity.Image;
import com.funeral.wulinacademy.web.repository.FolderImagesRepository;
import com.funeral.wulinacademy.web.repository.sql.SqlStatement;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:21
 */
@Repository
public class FolderImagesRepositoryImpl extends BaseRepositoryImpl<FolderImages,Integer> implements FolderImagesRepository {
    @Override
    public FolderImages save(Integer folderId, Integer imageId,String imageName) {
        return this.save(new FolderImages().setFolderId(folderId).setImageId(imageId).setImageName(imageName));
    }
}
