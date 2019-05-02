package com.funeral.wulinacademy.web.security;

import com.funeral.wulinacademy.web.config.ServiceConfig;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author FuneralObjects
 * @date 2019-04-13 00:16
 */
public class HeaderCsrfTokenRepository implements CsrfTokenRepository {
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(ServiceConfig.HEADER_IN_AUTH_TOKEN_NAME,ServiceConfig.PARAMETER_IN_AUTH_TOKEN_NAME, uuidToken());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(ServiceConfig.HEADER_IN_AUTH_TOKEN_NAME,token.getToken());
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String token = request.getHeader(ServiceConfig.HEADER_IN_AUTH_TOKEN_NAME);
        return StringUtils.isEmpty(token)
                ? null
                : new DefaultCsrfToken(ServiceConfig.HEADER_IN_AUTH_TOKEN_NAME,ServiceConfig.PARAMETER_IN_AUTH_TOKEN_NAME,token);
    }

    private String uuidToken(){
        return UUID.randomUUID().toString();
    }
}
