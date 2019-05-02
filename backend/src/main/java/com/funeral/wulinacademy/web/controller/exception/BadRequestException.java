package com.funeral.wulinacademy.web.controller.exception;

import javax.servlet.http.HttpServletResponse;


/**
 * 用于参数校验不同过异常
 *
 * @author FuneralObjects
 * @date 2019-04-25 01:47
 */
public class BadRequestException extends BaseServletException {
    private static final int STATUS = HttpServletResponse.SC_BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    @Override
    public int getStatus() {
        return STATUS;
    }
}
