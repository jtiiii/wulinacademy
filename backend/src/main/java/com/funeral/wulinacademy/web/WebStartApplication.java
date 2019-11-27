package com.funeral.wulinacademy.web;

import com.funeral.wulinacademy.web.config.ServiceSecurityConfig;
import com.funeral.wulinacademy.web.config.SpringSecurityConfig;
import com.funeral.wulinacademy.web.config.WebMvcConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

/**
 * Web服务启动的主类
 *
 * @author FuneralObjects
 * @date 2018-12-27 00:47
 */
@SpringBootApplication
@Import({SpringSecurityConfig.class, WebMvcConfig.class, ServiceSecurityConfig.class})
@EnableJpaRepositories
public class WebStartApplication {
    public static void main(String[] args){
        SpringApplication.run(WebStartApplication.class, args);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }
}
