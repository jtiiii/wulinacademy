package com.funeral.wulinacademy.web.repository;

import com.funeral.wulinacademy.web.entity.LoginUser;
import org.springframework.data.repository.Repository;

/**
 * @author FuneralObjects
 * @date 2019-04-20 01:24
 */
public interface LoginUserRepository extends Repository<LoginUser, String> {

    LoginUser findLoginUserByUsername(String username);
}
