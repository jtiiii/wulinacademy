package com.funeral.wulinacademy.web.controller.advice;

import com.funeral.wulinacademy.web.common.CommonLogger;
import com.funeral.wulinacademy.web.controller.exception.InternalServerErrorException;
import org.slf4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-15 00:19
 */
@RestControllerAdvice
@Order
public class DefaultExceptionHandler {

    private static final Logger logger = CommonLogger.CONTROLLER_LOGGER;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultExceptionHandler(HttpServletRequest request, Exception e){
        String errorId = ExceptionHandlerUtils.logErrorMessage(logger,request,e);
        return ExceptionHandlerUtils.generateResponseEntity(new InternalServerErrorException(errorId,e));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandler(HttpServletRequest request, RuntimeException re) {
        return this.defaultExceptionHandler(request,re);
    }

}
