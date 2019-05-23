package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.ImageRelate;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-18 15:21
 */
public interface ImageRelateRepository extends BaseRepository<ImageRelate,String> {
    List<ImageRelate> findAllByFolderId(Integer folderId);
}
