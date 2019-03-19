package com.funeral.wulinacademy.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Web服务启动的主类
 *
 * @author FuneralObjects
 * @date 2018-12-27 00:47
 */
@SpringBootApplication( scanBasePackages = {"com.funeral.wulinacademy.web.config"} )
public class WebStartApplication {
    public static void main(String[] args){
        SpringApplication.run(WebStartApplication.class, args);
    }
}
