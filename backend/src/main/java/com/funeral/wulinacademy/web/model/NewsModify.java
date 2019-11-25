package com.funeral.wulinacademy.web.model;

import com.funeral.wulinacademy.web.common.standard.StandardStatus;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    private StandardStatus status;
    private String thumbnail;
}