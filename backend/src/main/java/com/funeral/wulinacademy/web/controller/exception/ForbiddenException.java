package com.funeral.wulinacademy.web.controller.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * @author FuneralObjects
 * @date 2019-05-16 00:34
 */
public class ForbiddenException extends BaseServletException {

    private static final int STATUS = HttpServletResponse.SC_FORBIDDEN;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    @Override
    public int getStatus() {
        return STATUS;
    }
}
