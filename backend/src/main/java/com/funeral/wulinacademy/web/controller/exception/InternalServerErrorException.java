package com.funeral.wulinacademy.web.controller.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * @author FuneralObjects
 * @date 2019-04-25 02:04
 */
public class InternalServerErrorException extends BaseServletException {
    private static final int STATUS = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    @Override
    public int getStatus() {
        return STATUS;
    }
}
