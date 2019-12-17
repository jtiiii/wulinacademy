package com.funeral.wulinacademy.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * System check controller
 *
 * @author FuneralObjects
 * CreateTime 2018-12-27 19:11
 */
@RestController
@RequestMapping("/check")
public class CheckController {

    @GetMapping("/available")
    public String available(){
        return "SUCCESS";
    }

    @GetMapping("/login")
    public String login(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
