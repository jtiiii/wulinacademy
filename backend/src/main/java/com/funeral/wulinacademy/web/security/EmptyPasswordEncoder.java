package com.funeral.wulinacademy.web.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author FuneralObjects
 * @date 2019-04-20 10:31
 */
public class EmptyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
