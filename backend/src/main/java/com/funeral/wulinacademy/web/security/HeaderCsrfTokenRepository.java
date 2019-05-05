package com.funeral.wulinacademy.web.security;


import com.funeral.wulinacademy.web.config.ServiceSecurityConfig;
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

    private String headerName;
    private String paramName;
    private String stranger;

    public HeaderCsrfTokenRepository(ServiceSecurityConfig config) {
        this.headerName = config.getCsrf().getToken().getInHeaderName();
        this.paramName = config.getCsrf().getToken().getInParamName();
        this.stranger = config.getCsrf().getToken().getStranger();
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(headerName,paramName, uuidToken());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String tokenStr = token == null? stranger: token.getToken();
        request.getSession().setAttribute(headerName,tokenStr);
        response.setHeader("Access-Control-Expose-Headers",headerName);
        response.setHeader(headerName,tokenStr);
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute(headerName);
        return new DefaultCsrfToken(headerName,paramName,StringUtils.isEmpty(token)?stranger: token);
    }
    private String uuidToken(){
        return UUID.randomUUID().toString();
    }
}
