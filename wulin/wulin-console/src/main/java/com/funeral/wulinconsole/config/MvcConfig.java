package com.funeral.wulinconsole.config;

import com.funeral.wulinconsole.filter.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
/**
 * @Title:com.funeral.wulinacademyconsole.config
 * @Description:     MVC Config 配置类  相当于 以前的 servlet-mvc.xml
 * @Author:Jaskson_J
 * @Since:2019/1/4
 * @Version:V1.1.0
 * @company
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    //修改资源映射   classpath:/META-INF/resources/ 解决swagger2 访问问题
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 暂不清楚 SpringBoot 2.1.1 默认静态资源映射为啥不生效 这里进行重新配置一次
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations ("classpath:/resources/")
                .addResourceLocations ("classpath:/static/")
                .addResourceLocations ("classpath:/public/");
    }

    // 添加拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor (new LoginHandlerInterceptor ())
                .addPathPatterns ("/manager/**");
    }

}
