package com.funeral.wulinacademy.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring的基础配置
 *
 * @author FuneralObjects
 * @date 2018-12-27 00:50
 */
@EnableJdbcRepositories(basePackages = {"com.funeral.wulinacademy.web.repository"})
@EnableTransactionManagement
@Configuration
@ComponentScan({"com.funeral.wulinacademy.web.service"})
public class SpringConfig {

}
