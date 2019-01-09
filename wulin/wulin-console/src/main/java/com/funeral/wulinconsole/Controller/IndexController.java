package com.funeral.wulinconsole.Controller;

import com.funeral.wulinapi.Enum.ErrorCodeEnum;
import com.funeral.wulincore.exception.WuLinException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Title:com.funeral.wulinacademyconsole.Controller
 * @Description:  首页 Controller
 * @Author:Jackson_J
 * @Since:2019/1/4
 * @Version:V1.1.0
 */
@Controller
@RequestMapping("/mainBord")
public class IndexController extends BaseController {

    @RequestMapping("/textEx")
    public String homePage () {
        try {
            System.out.println (1/0);
        } catch (Exception ex) {
            throw  new WuLinException (ErrorCodeEnum.UNKNOWN_EXCEPTION,"除数不能为0");
        }
        return "manager-index";
    }
}
