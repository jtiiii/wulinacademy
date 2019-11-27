package com.funeral.wulinacademy.web.controller.advice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.funeral.wulinacademy.web.util.CollectionUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author FuneralObjects
 * Create date: 2019/11/27 3:15 PM
 */
@RestControllerAdvice
public class PageReturnAdvice  implements ResponseBodyAdvice<Page> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class valueClass = Objects.requireNonNull(returnType.getMethod()).getReturnType();
        return Page.class.isAssignableFrom(valueClass) && !SimplePage.class.isAssignableFrom(valueClass);
    }

    @Override
    public Page beforeBodyWrite(Page body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body == null){
            return null;
        }
        return new SimplePage<>()
                .setTotalElements(body.getTotalElements())
                .setTotalPages(body.getTotalPages())
                .setSize(body.getSize())
                .setNumberOfElements(body.getNumberOfElements())
                .setNumber(body.getNumber())
                .setContent(body.getContent());
    }

    @Data
    @Accessors(chain = true)
    public static class SimplePage<T> implements Page<T>{

        private int totalPages;
        private long totalElements;
        private int number;
        private int size;
        private int numberOfElements;
        private List<T> content;


        @Override
        public boolean hasContent() {
            return CollectionUtils.isEmpty(content);
        }

        @JsonIgnore
        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return number == 0;
        }

        @Override
        public boolean isLast() {
            return number == totalPages - 1;
        }

        @JsonIgnore
        @Override
        public boolean hasNext() {
            return !isLast();
        }

        @JsonIgnore
        @Override
        public boolean hasPrevious() {
            return !isFirst();
        }

        @JsonIgnore
        @Override
        public Pageable getPageable() {
            return PageRequest.of(number,size);
        }

        @JsonIgnore
        @Override
        public Pageable nextPageable() {
            return PageRequest.of(number + 1, size);
        }

        @JsonIgnore
        @Override
        public Pageable previousPageable() {
            return PageRequest.of(number -1 ,size);
        }

        @JsonIgnore
        @Override
        public <U> Page<U> map(Function<? super T, ? extends U> converter) {
            List<U> content = CollectionUtils.isEmpty(this.content)? Collections.emptyList(): this.content.parallelStream().map(converter).collect(Collectors.toList());
            return new SimplePage<U>()
                    .setContent(content)
                    .setNumber(this.number)
                    .setNumberOfElements(this.numberOfElements)
                    .setSize(this.size)
                    .setTotalPages(this.totalPages)
                    .setTotalElements(this.totalElements);
        }

        @JsonIgnore
        @Override
        public Iterator<T> iterator() {
            return CollectionUtils.isEmpty(this.content)? Collections.emptyIterator(): this.content.iterator();
        }
    }
}
