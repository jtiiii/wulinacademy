package com.funeral.wulinacademy.web.controller.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.funeral.wulinacademy.web.common.CommonLogger;
import com.funeral.wulinacademy.web.controller.exception.BadRequestException;
import com.funeral.wulinacademy.web.util.ClassUtils;
import com.funeral.wulinacademy.web.util.HttpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author FuneralObjects
 * @date 2019-04-26 03:33
 */
@Aspect
@Component
public class RestControllerAspect {

    private Logger logger = CommonLogger.CONTROLLER_LOGGER;

    @Pointcut( "@target(org.springframework.web.bind.annotation.RestController) && execution(public * com.funeral.wulinacademy.web.controller.*.*(..))")
    public void restController(){}

    @Pointcut( "@annotation(com.funeral.wulinacademy.web.controller.annotation.AutoValid) && @target(org.springframework.web.bind.annotation.RestController) && execution(public * com.funeral.wulinacademy.web.controller.*.*(..))")
    public void validateController(){}

    @Around("restController()")
    public Object debugLogger(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();
        if(logger.isDebugEnabled()){
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            MethodSignature signature = (MethodSignature) (pjp.getSignature());
            String method = signature.getDeclaringTypeName()+"#"+signature.getName();
            logger.debug("\r\n--- RestController Method Log ---\r\n{} \r\n Method: {} \r\n Return: {}",HttpUtils.information(request),method,ClassUtils.isVoid(signature.getReturnType()) ? "[void]" : new ObjectMapper().writeValueAsString(result));
        }
        return result;
    }

    @Before("validateController()")
    public void validateParam(JoinPoint jp) throws BadRequestException {
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            if(arg instanceof BindingResult){
                BindingResult br = (BindingResult) arg;
                if(br.hasErrors()){
                    StringBuilder message = new StringBuilder();
                    List<FieldError> fieldErrorList = br.getFieldErrors();
                    for (int i = 0; i < fieldErrorList.size(); i++) {
                        FieldError fieldError = fieldErrorList.get(i);
                        if( i > 0){
                            message.append(",");
                        }
                        message.append("[")
                                .append(fieldError.getField())
                                .append("]")
                                .append(fieldError.getDefaultMessage());
                    }
                    throw new BadRequestException(message.toString());
                }
                break;
            }
        }
    }
}
