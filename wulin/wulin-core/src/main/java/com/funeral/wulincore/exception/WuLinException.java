package com.funeral.wulincore.exception;

import com.funeral.wulinapi.Enum.ErrorCodeEnum;

/**
 * @Title:com.funeral.wulincore.exception
 * @Description:  武林 通用异常类
 * @Author:Jackson_J
 * @Since:2019/1/7
 * @Version:V1.1.0
 */
public class WuLinException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 建议使用  ErrorCodeEnum
     */
    private ErrorCodeEnum key;
    private Object[] values;

    public WuLinException() {
        super();
    }

    public WuLinException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public WuLinException(String message) {
        super(message);
    }

    public WuLinException(Throwable throwable) {
        super(throwable);
    }

    public WuLinException(ErrorCodeEnum key, String message) {
        super(message);
        this.key = key;
    }

    public WuLinException(ErrorCodeEnum key, Object value, String message) {
        super(message);
        this.key = key;
        this.values = new Object[] { value };
    }

    public WuLinException(ErrorCodeEnum key, Object[] values, String message) {
        super(message);
        this.key = key;
        this.values = values;
    }

    public ErrorCodeEnum getKey() {
        return key;
    }

    public Object[] getValues() {
        return values;
    }
}
