package com.funeral.wulinacademy.web.controller.model.search;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分页关键字查询
 *
 * @author FuneralObjects
 * @date 2019-03-29 14:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PageSearchWithKey extends SimplePage {
    /**
     * 关键字查询
     */
    private String key;
}
