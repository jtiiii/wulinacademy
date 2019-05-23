package com.funeral.wulinacademy.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web-mvc的配置
 *
 * @author FuneralObjects
 * @date 2018-12-27 12:50
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.funeral.wulinacademy.web.controller")
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${location.images}")
    private String imageLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:./images/");
    }

}
