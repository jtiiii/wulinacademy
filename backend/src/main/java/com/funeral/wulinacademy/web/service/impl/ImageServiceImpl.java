package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.entity.pk.FolderImagePk;
import com.funeral.wulinacademy.web.model.FolderImagesModify;
import com.funeral.wulinacademy.web.repository.FolderImagesRepository;
import com.funeral.wulinacademy.web.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author FuneralObjects
 * @date 2019-05-15 14:54
 */
@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    @Resource
    private FolderImagesRepository folderImagesRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addImage(FolderImagesModify modify) {
        Assert.notNull(modify, "The modify of news cannot be null");
        folderImagesRepository.save(new FolderImages()
                .setPk(new FolderImagePk().setFolderId(modify.getFolderId()).setImageName(modify.getImageName()))
                .setSha1Md5(modify.getSha1Md5())
                .setSuffix(modify.getSuffix()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer folderId, String name) {
        Assert.notNull(folderId, "The folderId cannot be null!");
        Assert.notNull(name, "The name cannot be null!");
        folderImagesRepository.deleteById(new FolderImagePk().setFolderId(folderId).setImageName(name));
    }

    @Override
    public List<FolderImages> findAllByFolderId(Integer folderId) {
        Assert.notNull(folderId, "The folderId cannot be null!");
        return folderImagesRepository.findAllByPk_FolderId(folderId);
    }
}
