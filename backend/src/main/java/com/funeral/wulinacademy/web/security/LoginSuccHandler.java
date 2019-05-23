package com.funeral.wulinacademy.web.security;

import com.funeral.wulinacademy.web.config.ServiceSecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FuneralObjects
 * @date 2019-04-12 23:56
 */
public class LoginSuccHandler implements AuthenticationSuccessHandler {


    public LoginSuccHandler(String tokenHeaderName) {
        this.tokenHeaderName = tokenHeaderName;
    }

    private String tokenHeaderName;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = response.getHeader(tokenHeaderName);
        response.getWriter().println(token);
    }
}
