package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.common.standard.StatusStandard;
import com.funeral.wulinacademy.web.entity.LoginUser;
import com.funeral.wulinacademy.web.repository.LoginUserRepository;
import com.funeral.wulinacademy.web.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author FuneralObjects
 * @date 2019-04-20 01:21
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Resource
    private LoginUserRepository loginUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = loginUserRepository.findLoginUserByUsername(username);
        if(loginUser == null){
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(username)
                .password(loginUser.getPassword())
                .accountLocked(!StatusStandard.isVISIBLE(loginUser.getStatus()))
                .authorities(new String[]{})
                .build();
    }

}
