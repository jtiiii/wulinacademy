package com.funeral.wulinacademy.web;

import com.funeral.wulinacademy.web.config.SpringConfig;
import com.funeral.wulinacademy.web.config.SpringSecurityConfig;
import com.funeral.wulinacademy.web.config.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Web服务启动的主类
 *
 * @author FuneralObjects
 * CreateTime 2018-12-27 00:47
 */
@SpringBootApplication
@Import({SpringConfig.class, WebMvcConfig.class, SpringSecurityConfig.class})
public class WebStartApplication {
    public static void main(String args[]){
        SpringApplication.run(WebStartApplication.class, args);
    }
}
