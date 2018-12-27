package com.funeral.wulinacademy.web.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * security的config配置
 *
 * @author FuneralObjects
 * CreateTime 2018-12-27 12:52
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO 先允许通过所有请求
        http
                .authorizeRequests()
                    .anyRequest()
                    .permitAll();
    }
}
