package com.funeral.wulinacademy.web.common.standard;

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

    private int num;

    StandardStatus(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

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

    public static boolean isVISIBLE(Integer num){
        return VISIBLE == of(num);
    }
}
