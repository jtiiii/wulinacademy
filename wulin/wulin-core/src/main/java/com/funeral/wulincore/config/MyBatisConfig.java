package com.funeral.wulincore.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
// Mybatis 配置类
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){

            @Override
            public void customize(Configuration configuration) {
                // 开启 mybatis 中的驼峰命名规则
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
