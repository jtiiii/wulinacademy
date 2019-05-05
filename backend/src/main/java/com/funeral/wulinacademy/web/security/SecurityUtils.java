package com.funeral.wulinacademy.web.security;

import com.funeral.wulinacademy.web.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author FuneralObjects
 * @date 2019-05-05 16:19
 */
public class SecurityUtils {
    public static boolean isLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return false;
        }
        return auth.isAuthenticated();
    }

    public static LoginUser getCurrentLoginUser(){
        return null;
    }
}
