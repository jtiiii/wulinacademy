package com.funeral.wulinacademy.web.controller.model.search;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一些通用的查询条件
 *
 * @author FuneralObjects
 * CreateTime 2019-03-20 10:07
 */
@Data
@Accessors(chain = true)
public class CommonSearchRequest extends SearchRequest {
    /**
     * 单条主键
     */
    private String id;
    /**
     * 主键集合
     */
    private List<String> ids;
    /**
     * 编号
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 状态
     */
    private Integer state;

    public void setIds(String ids) {
        this.ids = StringUtils.isEmpty(ids) || StringUtils.isEmpty(ids.trim()) ?
                new ArrayList<>() :
                Arrays.asList(ids.trim().split(","));
    }
}
