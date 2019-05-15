package com.funeral.wulinacademy.web.controller.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * @author FuneralObjects
 * @date 2019-05-14 23:33
 */
public class NotAcceptableException extends BaseServletException {

    private static final int STATUS = HttpServletResponse.SC_NOT_ACCEPTABLE;

    public NotAcceptableException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public NotAcceptableException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return STATUS;
    }
}
