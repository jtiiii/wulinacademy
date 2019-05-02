package com.funeral.wulinacademy.web.controller.advice;

import com.funeral.wulinacademy.web.common.CommonLogger;
import com.funeral.wulinacademy.web.common.standard.DateStandard;
import com.funeral.wulinacademy.web.controller.exception.BadRequestException;
import com.funeral.wulinacademy.web.controller.exception.BaseServletException;
import com.funeral.wulinacademy.web.controller.exception.InternalServerErrorException;
import com.funeral.wulinacademy.web.util.HttpUtils;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author FuneralObjects
 * @date 2019-04-25 02:00
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger logger = CommonLogger.CONTROLLER_LOGGER;

    private volatile AtomicInteger count = new AtomicInteger(0);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandler(HttpServletRequest request, RuntimeException re) {
        StringBuilder errorId = new StringBuilder("[").append(request.getMethod()).append("]:").append(generateErrorId(request));
        logger.error("\r\n ERROR-ID: {} \r\n {} \r\n Error: {} \r\n Message: {}",errorId, HttpUtils.information(request),re.getClass(),re.getMessage());
        logger.error(errorId.toString(),re);
        return this.servletError(new InternalServerErrorException(errorId.toString(),re));
    }

    @ExceptionHandler(BaseServletException.class)
    public ResponseEntity<String> servletError(BaseServletException bse){
        return ResponseEntity.status(bse.getStatus()).contentType(MediaType.TEXT_PLAIN).header("Content-Type",MediaType.TEXT_PLAIN_VALUE+";charset=utf-8").body(bse.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(HttpMessageNotReadableException hmnre){
        return this.servletError(new BadRequestException(hmnre.getMessage(),hmnre));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException matme){
        return this.servletError(new BadRequestException(matme.getMessage(),matme));
    }

    private String generateErrorId(HttpServletRequest request){
        return request.getSession().getId()+ ":["+new SimpleDateFormat(DateStandard.DATE_TIME_FORMAT).format(new Date())+ "]:" + count.addAndGet(1);
    }

}
