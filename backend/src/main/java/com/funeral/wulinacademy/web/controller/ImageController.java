package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.controller.exception.BadRequestException;
import com.funeral.wulinacademy.web.controller.model.image.ImageVo;
import com.funeral.wulinacademy.web.model.FolderImagesModify;
import com.funeral.wulinacademy.web.model.ImageFile;
import com.funeral.wulinacademy.web.service.ImageService;
import com.funeral.wulinacademy.web.util.MultipartFileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author FuneralObjects
 * CreateTime 2019-05-15 15:24
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    private ImageService imageService;

    @Value("${upload.image.max-size}")
    private long maxSize;

    @Value("${location.images}")
    private String imageDir;

    @PostConstruct
    private void inti(){
        File dir = new File(imageDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    @PostMapping
    public void upload(@RequestParam("name") String name,
                       @RequestParam("file") MultipartFile file,
                       @RequestParam("folder") Integer folder) {
        limitSize(file.getSize());
        ImageFile imageFile = MultipartFileUtils.toImageFile(file);
        MultipartFileUtils.saveIfNotExistsFileInSystem(getFileName(imageFile.getSha1Md5() + "." + imageFile.getSuffix()),imageFile.getContent());
        imageService.addImage(new FolderImagesModify()
                .setFolderId(folder)
                .setImageName(name)
                .setSha1Md5(imageFile.getSha1Md5())
                .setSuffix(imageFile.getSuffix())
        );
    }

    @GetMapping("/folder/{folderId}")
    public List<ImageVo> findImageVoByFolderId(@PathVariable Integer folderId){
        return imageService.findAllByFolderId(folderId)
                .parallelStream()
                .map( fi -> new ImageVo()
                        .setName(fi.getPk().getImageName())
                        .setSha1Md5(fi.getSha1Md5())
                        .setSuffix(fi.getSuffix()))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/folder/{folderId}/{name}")
    public void deleteImage(@PathVariable Integer folderId,
                            @PathVariable String name){
        imageService.delete(folderId,name);
    }


    private void limitSize(long size){
        if(size > maxSize ){
            throw new BadRequestException("This image uploaded is exceed the limit["+maxSize+" b].");
        }
    }
    private String getFileName(String name){
        return imageDir + name;
    }

}
