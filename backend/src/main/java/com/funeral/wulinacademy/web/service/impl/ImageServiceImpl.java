package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.entity.FolderImages;
import com.funeral.wulinacademy.web.entity.Image;
import com.funeral.wulinacademy.web.entity.ImageRelate;
import com.funeral.wulinacademy.web.repository.FolderImagesRepository;
import com.funeral.wulinacademy.web.repository.FolderRepository;
import com.funeral.wulinacademy.web.repository.ImageRelateRepository;
import com.funeral.wulinacademy.web.repository.ImageRepository;
import com.funeral.wulinacademy.web.service.FolderService;
import com.funeral.wulinacademy.web.service.ImageService;

import com.funeral.wulinacademy.web.service.exception.ValidateException;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-05-15 14:54
 */
@Service
public class ImageServiceImpl implements ImageService {

    @Value("${location.images}")
    private String imageLocation;

    @Resource
    private ImageRepository imageRepository;
    @Resource
    private FolderImagesRepository folderImagesRepository;
    @Resource
    private FolderRepository folderRepository;
    @Resource
    private ImageRelateRepository imageRelateRepository;


    @PostConstruct
    private void init(){
        File imageFolder = new File(imageLocation);
        if(!imageFolder.exists() || imageFolder.isFile()){
            imageFolder.mkdir();
        }
    }

    @Override
    public void saveOrUpdate(Image image) {
        imageRepository.save(image);
    }

    @Override
    public void saveImageFolder(Integer folderId, Integer imageId) {
        folderImagesRepository.save(new FolderImages().setFolderId(folderId).setImageId(imageId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(MultipartFile file, Integer folderId, String imageName) throws ValidateException {
        if(StringUtils.isEmpty(imageName)){
            throw new ValidateException("Image's name is empty!");
        }
        if(!this.folderRepository.existsById(folderId)){
            throw new ValidateException("Invalid folderId["+folderId+"]");
        }

        String[] info = this.upload(file);
        Image image = imageRepository.findByMd5(info[0]);
        if(image == null) {
            image = new Image().setMd5(info[0]).setSite(info[1]).setSuffix(info[2]);
            imageRepository.save(image);
        }
        folderImagesRepository.save(folderId,image.getImageId(),imageName);
    }

    @Override
    public List<ImageRelate> findFolderImages(Integer folderId) throws ValidateException {
        if(FolderService.ROOT_ID.equals(folderId)){
            throw new ValidateException("Invalid folderId, cannot find by ROOT[0] folder.");
        }
        return imageRelateRepository.findAllByFolderId(folderId);
    }


    private String[] upload(MultipartFile file) {
        String [] result = new String[3];
        try {
            byte[] bytes = file.getBytes();
            String md5 = this.encode(bytes);
            result[0] = md5;
            String original = file.getOriginalFilename();
            if(original == null){
                throw new RuntimeException("Not filename.");
            }
            String suffix = this.suffix(file.getOriginalFilename());
            String fileName = this.imageLocation + md5 + suffix;
            result[1] = fileName;
            result[2] = suffix;
            File save = new File(fileName);
            if(save.exists()){
                return result;
            }
            FileCopyUtils.copy(bytes,save);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String encode(byte[] bytes){
        return DigestUtils.md5DigestAsHex(bytes);
    }

    private String suffix(String name){
        return name.substring(name.lastIndexOf("."));
    }


}
