package com.funeral.wulinacademy.web.controller;

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
@RequestMapping("/system-check")
public class SystemCheckController {

    @GetMapping("/connection")
    public String connection(){
        return "SUCCESS";
    }
}
