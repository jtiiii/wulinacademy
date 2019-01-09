package com.funeral.wulinconsole.Controller.RestApi;

import com.funeral.wulinconsole.Controller.BaseController;
import com.funeral.wulincore.utils.RestApiJSONResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title:com.funeral.wulinconsole.Controller.RestApi
 * @Description:  Rest API 接口
 * @Author: Jackson_J
 * @Since:2019/1/7
 * @Version:V1.1.0
 * @company Copyright © 2018 深圳中电国际信息科技有限公司版权所有
 */
@RestController
@Api(value="武林书画学院后台首页的接口", tags= {"首页相关的controller"})
@RequestMapping("/wuLinApi")
public class APIController extends BaseController {

    @ApiOperation (value="测试 Swagger2 ", notes="say")
    // 这个是表单提交多参数的绑定形式
   @ApiImplicitParams ({
            @ApiImplicitParam(name="userName", value="用户名", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="message", value="自然语言", required=true,
                    dataType="String", paramType="form")
    })
    @PostMapping("/testSay")
    public RestApiJSONResult say(String userName, String message) {
        String str = userName + ":--" + message;
        return RestApiJSONResult.build (200,str,null);
    }

}
