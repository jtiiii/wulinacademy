package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.Image;

import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-14 13:54
 */
public interface ImageRepository extends BaseRepository<Image,Integer> {
    Image findByMd5(String md5);

}
