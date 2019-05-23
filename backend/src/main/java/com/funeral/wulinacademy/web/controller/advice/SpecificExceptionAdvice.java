package com.funeral.wulinacademy.web.controller.advice;

import com.funeral.wulinacademy.web.controller.exception.BadRequestException;
import com.funeral.wulinacademy.web.controller.exception.BaseServletException;
import com.funeral.wulinacademy.web.controller.exception.NotAcceptableException;
import com.funeral.wulinacademy.web.service.exception.BusinessException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

/**
 * @author FuneralObjects
 * @date 2019-04-25 02:00
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecificExceptionAdvice {

    @ExceptionHandler(BaseServletException.class)
    public ResponseEntity<String> servletError(BaseServletException bse){
        return ExceptionHandlerUtils.generateResponseEntity(bse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> businessExceptionHandler(BusinessException be){
        return this.servletError(new NotAcceptableException(be.getMessage(),be));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> multipartExceptionHandler(MultipartException me){
        return this.servletError(new BadRequestException(me.getMessage(),me));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(HttpMessageNotReadableException hmnre){
        return this.servletError(new BadRequestException(hmnre.getMessage(),hmnre));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme){
        return this.servletError(new BadRequestException(matme.getMessage(),matme));
    }

}
