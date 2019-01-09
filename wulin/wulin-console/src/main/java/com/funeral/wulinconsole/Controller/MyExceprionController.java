package com.funeral.wulinconsole.Controller;

import com.funeral.wulincore.exception.WuLinException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:com.funeral.wulinconsole.Controller
 * @Description:  自定义 异常处理
 *                设置一个 断言Controller 当在Controller 中 抛出自定义的 WuLinException 后 进入该方法
 * @Author: Jackson_J
 * @Since:2019/1/7
 * @Version:V1.1.0
 * @company Copyright © 2018 深圳中电国际信息科技有限公司版权所有
 */
@ControllerAdvice
public class MyExceprionController extends BaseController{

    @ExceptionHandler(WuLinException.class)
    public String handleException(WuLinException wuLinException){
        Map<String,Object> map = new HashMap<String,Object> ();
        request.setAttribute("javax.servlet.error.status_code",wuLinException.getKey ().getErrorCode ());
        map.put("code",wuLinException.getKey ().getErrorType ());
        map.put("message",wuLinException.getKey ().getErrorMsg ());
        request.setAttribute("ext",map);
        //转发到/error
        return "forward:/error";
    }
}
