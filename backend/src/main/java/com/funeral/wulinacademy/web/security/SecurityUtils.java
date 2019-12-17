package com.funeral.wulinacademy.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-05 16:19
 */
public class SecurityUtils {
    public static boolean isLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return false;
        }
        if(auth.getPrincipal() instanceof  String && "anonymousUser".equals(auth.getPrincipal()) ){
            return false;
        }
        return auth.isAuthenticated();
    }

    public static String getCurrentLoginUsername(){
        if(!isLogin()){
            return null;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getUsername();
    }
}
