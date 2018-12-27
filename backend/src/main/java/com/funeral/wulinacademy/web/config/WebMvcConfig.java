package com.funeral.wulinacademy.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * web-mvc的配置
 *
 * @author FuneralObjects
 * CreateTime 2018-12-27 12:50
 */
@EnableWebMvc
@ComponentScan("com.funeral.wulinacademy.web.controller")
public class WebMvcConfig {

}
