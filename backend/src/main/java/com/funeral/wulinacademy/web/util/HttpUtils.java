package com.funeral.wulinacademy.web.util;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author FuneralObjects
 * @date 2019-04-25 02:14
 */
public class HttpUtils {

    public static String information(HttpServletRequest request){
        return new HttpRequestInformation(request).toString();
    }

    public static String body(HttpServletRequest request){
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if(wrapper == null){
            BufferedReader reader;
            try {
                reader = request.getReader();
                StringBuilder result = new StringBuilder();
                String temp;
                while ( (temp = reader.readLine()) != null ){
                    result.append(temp);
                }
                reader.close();
                return result.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        byte[] buf = wrapper.getContentAsByteArray();
        try {
            return new String(buf, 0, buf.length, wrapper.getCharacterEncoding()).replaceAll("\n","").replaceAll("\t"," ");
        } catch (UnsupportedEncodingException e) {
            return "[unknown]";
        }
    }

    public static Map<String,String> parameters(HttpServletRequest request){
        int size = request.getParameterMap().size();
        Map<String,String> result = new HashMap<>(size);
        request.getParameterMap().forEach( (key,values) -> result.put(key, values == null? "":Arrays.toString(values)));
        return result;
    }

    public static Map<String,String> headers(HttpServletRequest request){
        Enumeration<String> enumerations = request.getHeaderNames();
        List<String[]> headerList = new LinkedList<>();
        while (enumerations.hasMoreElements()){
            String name = enumerations.nextElement();
            String header = request.getHeader(name);
            headerList.add(new String[]{name,header});
        }
        Map<String,String> result = new HashMap<>(headerList.size());
        headerList.forEach( header -> result.put(header[0],header[1]));
        return result;
    }

    public static Map<String,String> cookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return new HashMap<>(0);
        }
        Map<String,String> result = new HashMap<>(request.getCookies().length);

        for (Cookie cookie : request.getCookies()) {
            result.put(cookie.getName(),cookie.getValue());
        }
        return result;
    }

    @Data
    @Accessors(chain = true)
    private static class HttpRequestInformation {
        private String method;
        private String body;
        private String url;
        private String contentType;
        private Map<String,String> headers;
        private Map<String,String> cookies;
        private Map<String,String> parameters;

        HttpRequestInformation(HttpServletRequest request) {
            this.method = request.getMethod();
            this.body = body(request);
            this.url = request.getServletPath();
            this.parameters = parameters(request);
            this.headers = headers(request);
            this.cookies = cookies(request);
            this.contentType = request.getContentType();
        }

        @Override
        public String toString() {
            return "Request:" +
                    "\r\n\tUrl: " + url +
                    "\r\n\tMethod: " + method +
                    "\r\n\tHeaders:" + headers +
                    "\r\n\tParameters: " + parameters +
                    "\r\n\tCookies: " + cookies +
                    "\r\n\tContent-Type: "+ contentType +
                    "\r\n\tBody: " + body;
        }
    }
}
