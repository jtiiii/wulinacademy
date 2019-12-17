package com.funeral.wulinacademy.web.service.exception;

/**
 * 业务异常
 * @author FuneralObjects
 * CreateTime 2019-04-18 11:51
 */
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
