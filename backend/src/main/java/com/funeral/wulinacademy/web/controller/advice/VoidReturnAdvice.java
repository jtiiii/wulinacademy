package com.funeral.wulinacademy.web.controller.advice;

import com.funeral.wulinacademy.web.util.ClassUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author FuneralObjects
 * CreateTime 2019-04-27 01:08
 */
@RestControllerAdvice
public class VoidReturnAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //如果是void返回类型的话进行处理
        return ClassUtils.isVoid(Objects.requireNonNull(returnType.getMethod()).getReturnType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        response.setStatusCode(HttpStatus.NO_CONTENT);
        return body;
    }
}
