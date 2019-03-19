package com.funeral.wulinacademy.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * web-mvc的配置
 *
 * @author FuneralObjects
 * @date 2018-12-27 12:50
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.funeral.wulinacademy.web.controller")
public class WebMvcConfig {

}
