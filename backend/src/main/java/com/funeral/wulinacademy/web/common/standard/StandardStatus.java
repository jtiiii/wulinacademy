package com.funeral.wulinacademy.web.common.standard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author FuneralObjects
 * @date 2019-04-14 02:43
 */
public enum StandardStatus {
    /**
     * 可见
     */
    VISIBLE(1),
    /**
     * 不可见
     */
    INVISIBLE(0),
    /**
     * 删除的
     */
    DELETED(-1);

    @JsonValue
    @Getter
    private int num;

    StandardStatus(int num) {
        this.num = num;
    }

    @JsonCreator
    public static StandardStatus of(Integer num){
        if( num == null){
            return null;
        }
        switch (num){
            case 1: return VISIBLE;
            case 0: return INVISIBLE;
            case -1: return DELETED;
            default: return null;
        }
    }

    public static boolean isVisible(Integer num){
        return VISIBLE == of(num);
    }
}
