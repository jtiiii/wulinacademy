package com.funeral.wulinacademy.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author FuneralObjects
 * Create date: 2019/11/21 11:41 PM
 */
@Data
@Accessors(chain = true)
public class NewsModify {
    private String title;
    private Date eventDate;
    private String preview;
    private String thumbnail;
}