package com.funeral.wulinconsole.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Title:com.funeral.wulinacademyconsole.Controller
 * @Description:  公共Controller 提供一些公用功能
 * @Author:Jaskson_J
 * @Since:2019/1/4
 * @Version:V1.1.0
 */

public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        this.request = request;
        this.response = response;
        this.session = session;
    }
}
