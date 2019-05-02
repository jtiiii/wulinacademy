package com.funeral.wulinacademy.web.controller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.servlet.ServletException;

/**
 * Servlet访问基础异常类
 * 扩展
 *
 * @author FuneralObjects
 * @date 2019-04-25 01:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public abstract class BaseServletException extends Error {
    public BaseServletException(String message) {
        super(message);
    }

    public BaseServletException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public abstract int getStatus();
}
