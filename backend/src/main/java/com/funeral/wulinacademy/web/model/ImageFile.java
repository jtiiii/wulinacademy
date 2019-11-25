package com.funeral.wulinacademy.web.model;

import com.funeral.wulinacademy.web.util.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author FuneralObjects
 * Create date: 2019/11/21 11:08 PM
 */
@Data
@Accessors(chain = true)
public class ImageFile {
    private static final ConcurrentMap<String, MediaType> SUPPORT_SUFFIX_MEDIA_TYPE_MAP = new ConcurrentHashMap<>();

    static{
        SUPPORT_SUFFIX_MEDIA_TYPE_MAP.put("png",MediaType.IMAGE_PNG);
        SUPPORT_SUFFIX_MEDIA_TYPE_MAP.put("jpg",MediaType.IMAGE_JPEG);
        SUPPORT_SUFFIX_MEDIA_TYPE_MAP.put("jpeg", MediaType.IMAGE_JPEG);
        SUPPORT_SUFFIX_MEDIA_TYPE_MAP.put("gif", MediaType.IMAGE_GIF);
    }

    private String sha1Md5;
    private byte[] content;
    private String suffix;


    public ImageFile(String sha1Md5, byte[] content, String suffix) {
        suffix = processSuffix(suffix);
        if(!SUPPORT_SUFFIX_MEDIA_TYPE_MAP.containsKey(suffix)){
            throw new IllegalArgumentException("Not support this image type:["+suffix+"]");
        }
        this.sha1Md5 = sha1Md5;
        this.content = content;
        this.suffix = suffix;
    }


    private static String processSuffix(String suffix){
        if(StringUtils.isEmpty(suffix)){
            throw new IllegalArgumentException("Invalid value of suffix!");
        }
        if(suffix.startsWith(".")){
            suffix = suffix.substring(1);
        }
        return suffix.toLowerCase();
    }
}
