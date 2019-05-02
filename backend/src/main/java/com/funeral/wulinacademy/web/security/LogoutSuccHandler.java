package com.funeral.wulinacademy.web.security;

import com.funeral.wulinacademy.web.config.ServiceConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author FuneralObjects
 * @date 2019-04-20 15:29
 */
public class LogoutSuccHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setHeader(ServiceConfig.HEADER_IN_AUTH_TOKEN_NAME,null);
        response.setStatus(204);
    }
}
