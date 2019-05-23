package com.funeral.wulinacademy.web.service;

import com.funeral.wulinacademy.web.entity.Image;
import com.funeral.wulinacademy.web.entity.ImageRelate;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-15 14:53
 */
public interface ImageService {

    void saveOrUpdate(Image image);

    void saveImageFolder(Integer folderId, Integer imageId);

    void save(MultipartFile file,Integer folderId, String imageName) throws ValidateException;

    List<ImageRelate> findFolderImages(Integer folderId) throws ValidateException;
}
