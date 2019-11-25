package com.funeral.wulinacademy.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * Create date: 2019/11/21 11:06 PM
 */
@Data
@Accessors(chain = true)
public class NewsFile {
    /**
     * uuid 索引
     */
    private String uuid;
    /**
     * 内容
     */
    private byte[] content;
}
