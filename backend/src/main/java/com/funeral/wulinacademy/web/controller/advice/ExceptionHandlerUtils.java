package com.funeral.wulinacademy.web.controller.advice;

import com.funeral.wulinacademy.web.common.standard.DateStandard;
import com.funeral.wulinacademy.web.controller.exception.BaseServletException;
import com.funeral.wulinacademy.web.util.HttpUtils;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author FuneralObjects
 * @date 2019-05-15 00:21
 */
public class ExceptionHandlerUtils {

    private static final AtomicLong count = new AtomicLong(0);

    private static String generateErrorId(HttpServletRequest request){
        return request.getSession().getId()+ ":["+new SimpleDateFormat(DateStandard.DATE_TIME_FORMAT).format(new Date())+ "]:" + count.addAndGet(1);
    }

    public static String logErrorMessage(Logger logger, HttpServletRequest request, Exception e){
        StringBuilder errorId = new StringBuilder("[").append(request.getMethod()).append("]:").append(generateErrorId(request));
        logger.error("\r\n ERROR-ID: {} \r\n {} \r\n Error: {} \r\n Message: {}",errorId, HttpUtils.information(request),e.getClass(),e.getMessage());
        logger.error(errorId.toString(),e);
        return errorId.toString();
    }

    public static ResponseEntity<String> generateResponseEntity(BaseServletException bse){
        return ResponseEntity.status(bse.getStatus()).header("Content-Type",MediaType.TEXT_PLAIN_VALUE+";charset=utf-8").body(bse.getMessage());
    }

}
