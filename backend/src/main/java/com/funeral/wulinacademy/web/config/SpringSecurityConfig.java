package com.funeral.wulinacademy.web.config;

import com.funeral.wulinacademy.web.security.EmptyPasswordEncoder;
import com.funeral.wulinacademy.web.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * security的config配置
 *
 * @author FuneralObjects
 * @date 2018-12-27 12:52
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserService userService;

    private CorsConfigurationSource corsConfigurationSource;

    @Value("${cors.allow-origin}")
    private String CORS_ALLOW_ORIGIN;


    @PostConstruct
    private void init(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin(CORS_ALLOW_ORIGIN);
        source.registerCorsConfiguration("/**",configuration);
        this.corsConfigurationSource = source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO 先允许通过所有请求
//        http
//                .authorizeRequests()
//                    .antMatchers("/check/available").permitAll()
//                    .anyRequest()
//                    .authenticated()
//                    .and()
//                .formLogin()
//                    .loginProcessingUrl("/login")
//                    .successHandler( new AuthSuccHandler())
//                    .failureHandler( new AuthFailHandler())
//                    .usernameParameter(ServiceConfig.USERNAME_OF_LOGIN_NAME)
//                    .passwordParameter(ServiceConfig.PASSWORD_OF_LOGIN_NAME)
//                    .and()
//                .logout()
////                    .logoutUrl("/logout")
//                    .logoutSuccessUrl("/logout")
//                    .logoutSuccessHandler(new LogoutSuccHandler())
//                    .and()
//                .cors()
//                    .configurationSource(corsConfigurationSource)
//                    .and()
//                .csrf()
//                    .disable();
//                    .ignoringAntMatchers("/login")
//                    .csrfTokenRepository(new HeaderCsrfTokenRepository());

        http
                .authorizeRequests()
                    .anyRequest()
                    .permitAll()
                    .and()
                .csrf().disable()
                .cors()
                    .configurationSource(corsConfigurationSource);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new EmptyPasswordEncoder());
    }
}
