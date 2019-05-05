package com.funeral.wulinacademy.web.config;

import com.funeral.wulinacademy.web.security.*;
import com.funeral.wulinacademy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
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

    private ServiceSecurityConfig serviceConfig;

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
        http
                .authorizeRequests()
                    .antMatchers("/check/available").permitAll()
                    .antMatchers(HttpMethod.GET,"/news/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .successHandler( new LoginSuccHandler())
                    .failureHandler( new LoginFailHandler())
                    .usernameParameter(serviceConfig.getLogin().getUsernameOfParamName())
                    .passwordParameter(serviceConfig.getLogin().getPasswordOfParamName())
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(new LogoutSuccHandler())
                    .and()
                .cors()
                    .configurationSource(corsConfigurationSource)
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                    .accessDeniedHandler(new NotRedirectAccessDeniedHandler())
                    .and()
                .csrf()
                    .ignoringAntMatchers("/login","/logout")
                    .csrfTokenRepository(new HeaderCsrfTokenRepository(serviceConfig));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService).passwordEncoder(new EmptyPasswordEncoder());
    }

    @Autowired
    public void setServiceConfig(ServiceSecurityConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }
}
