package com.funeral.wulinacademy.web.util;

import com.funeral.wulinacademy.web.model.ImageFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author FuneralObjects
 * Create date: 2019/11/21 11:12 PM
 */
public class MultipartFileUtils {

    public static ImageFile toImageFile(MultipartFile file){
        try {
            byte[] raw = file.getBytes();
            String md5 = DigestUtils.md5Hex(raw);
            String sha1 = DigestUtils.sha1Hex(raw);
            String sha1Md5 = sha1 + ":" + md5;
            String suffix = suffix(Objects.requireNonNull(file.getOriginalFilename()));
            return new ImageFile(sha1Md5,raw,suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveIfNotExistsFileInSystem(String name, byte[] content){
        File file = new File(name);
        if(file.exists()){
            return;
        }
        try {
            FileCopyUtils.copy(content,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String suffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
