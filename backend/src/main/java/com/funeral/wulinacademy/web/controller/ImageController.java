package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.controller.exception.BadRequestException;
import com.funeral.wulinacademy.web.controller.model.image.ImageModel;
import com.funeral.wulinacademy.web.entity.ImageRelate;

import com.funeral.wulinacademy.web.service.ImageService;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import com.funeral.wulinacademy.web.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author FuneralObjects
 * @date 2019-05-15 15:24
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private ImageService imageService;

    @Autowired
    private FolderController folderController;

    @Value("${upload.image.max-size}")
    private long maxSize;

    @PostMapping
    public void upload(@RequestParam("name") String name,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("folder") Integer folder) throws ValidateException {
        limitSize(file.getSize());
        imageService.save(file,folder,name);
    }

    @GetMapping(params = "folder")
    public List<ImageModel> getFoldersImage(@RequestParam("folder") Integer folderId) throws ValidateException {
        folderController.validateFolderAuthorityByCurrentUser(folderId);
        return relateToModel(imageService.findFolderImages(folderId));
    }

    private void limitSize(long size){
        if(size > maxSize ){
            throw new BadRequestException("This image uploaded is exceed the limit["+maxSize+" b].");
        }
    }

    private ImageModel relateToModel(ImageRelate relate){
        ImageModel mode =  new ImageModel()
                .setId(relate.getImageId())
                .setSuffix(relate.getSuffix())
                .setCreateTime(relate.getCreateTime());
        mode
                .setMd5(relate.getMd5())
                .setName(relate.getImageName())
                .setSite(relate.getSite())
                .setStatus(relate.getStatus());
        return mode;
    }

    private List<ImageModel> relateToModel(Collection<ImageRelate> relates){
        if(CollectionUtils.isEmptyAfterRemoveNull(relates)){
            return new ArrayList<>(0);
        }
        return relates.parallelStream().map(this::relateToModel).collect(Collectors.toList());
    }

}
