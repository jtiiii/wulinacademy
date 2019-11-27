package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.config.ServiceSecurityConfig;
import com.funeral.wulinacademy.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author FuneralObjects
 * @date 2019-04-20 01:21
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private ServiceSecurityConfig serviceSecurityConfig;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(!serviceSecurityConfig.getUser().getUsername().equals(username)){
            throw new UsernameNotFoundException("The username["+username+"] doesn't exists!");
        }
        return User.withUsername(username)
                .password(serviceSecurityConfig.getUser().getPassword())
                .accountLocked(false)
                .authorities(new String[]{})
                .build();
    }

}
