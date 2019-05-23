package com.funeral.wulinacademy.web.entity;

/**
 * @author FuneralObjects
 * @date 2019-05-18 15:47
 */
public class Empty implements ParseId<String> {
    @Override
    public String parse(String id) {
        return id;
    }
}
