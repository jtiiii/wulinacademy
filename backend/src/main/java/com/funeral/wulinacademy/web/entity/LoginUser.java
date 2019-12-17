package com.funeral.wulinacademy.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author FuneralObjects
 * CreateTime 2019-04-20 01:25
 */
@Data
@Accessors(chain = true)
public class LoginUser {
    private String username;
    private String password;
    private Integer status;
}
