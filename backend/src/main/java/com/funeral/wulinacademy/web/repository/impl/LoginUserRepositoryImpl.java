package com.funeral.wulinacademy.web.repository.impl;

import com.funeral.wulinacademy.web.common.factory.YamlPropertiesSourceFactory;
import com.funeral.wulinacademy.web.entity.LoginUser;
import com.funeral.wulinacademy.web.repository.LoginUserRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FuneralObjects
 * @date 2019-04-20 01:24
 */
@Repository
@PropertySource(factory = YamlPropertiesSourceFactory.class,value = "classpath:service-config.yml")
@ConfigurationProperties(prefix = "login-users")
public class LoginUserRepositoryImpl implements LoginUserRepository {

    private List<LoginUser> users;

    private Map<String,LoginUser> userMap;

    @PostConstruct
    private void initMapUser(){
        this.userMap = new HashMap<>(users.size());
        users.forEach( user -> userMap.put(user.getUsername(),user));
    }

    @Override
    public LoginUser findLoginUserByUsername(String username) {
        return userMap.get(username);
    }


    public List<LoginUser> getUsers() {
        return users;
    }

    public void setUsers(List<LoginUser> users) {
        this.users = users;
    }
}
