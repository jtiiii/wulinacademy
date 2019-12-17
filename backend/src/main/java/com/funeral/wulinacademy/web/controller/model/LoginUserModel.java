package com.funeral.wulinacademy.web.controller.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * vo of user for login
 *
 * @author FuneralObjects
 * CreateTime 2019-04-11 18:05
 */
@Data
@Accessors(chain = true)
public class LoginUserModel {
    private String username;
    private String password;
}
