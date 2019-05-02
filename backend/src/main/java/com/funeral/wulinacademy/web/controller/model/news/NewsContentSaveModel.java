package com.funeral.wulinacademy.web.controller.model.news;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * @date 2019-05-01 05:01
 */
@Data
@Accessors(chain = true)
public class NewsContentSaveModel {
    private String content;
}
