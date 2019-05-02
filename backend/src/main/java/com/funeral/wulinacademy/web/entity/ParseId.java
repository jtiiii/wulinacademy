package com.funeral.wulinacademy.web.entity;

/**
 * @author FuneralObjects
 * @date 2019-04-17 00:49
 */
public interface ParseId<ID> {



    ID parse(String id);

    static Integer parseInteger(String str){

        return Integer.valueOf(str);
    }
}
