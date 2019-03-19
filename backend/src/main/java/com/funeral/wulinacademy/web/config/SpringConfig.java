package com.funeral.wulinacademy.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Spring的基础配置
 *
 * @author FuneralObjects
 * @date 2018-12-27 00:50
 */
@EnableJdbcRepositories(basePackages = {"com.funeral.wulinacademy.web.repository"})
@EnableTransactionManagement
@Configuration
@ComponentScan("com.funeral.wulinacademy.web.service")
public class SpringConfig {

//    @Bean
//    public DataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl("jdbc:mysql://localhost:3306/dev_wulinacademy?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
//        dataSource.setUsername("wulin_dev");
//        dataSource.setPassword("123456");
//        dataSource.setMaxActive(5);
//        dataSource.setMaxWait(60000);
//        return dataSource;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate){
        return new SimpleJdbcInsert(jdbcTemplate);
    }

}
