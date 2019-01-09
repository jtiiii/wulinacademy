package com.funeral.wulinconsole.Controller;

import com.funeral.wulinapi.vo.baseVo.KeyVo;
import com.funeral.wulincore.common.ProjectUtil;
import com.funeral.wulincore.utils.MapUtil;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Title:com.funeral.wulinconsole.Controller
 * @Description:  登录Controller
 * @Author: Jackson_J
 * @Since:2019/1/7
 * @Version:V1.1.0
 * @company Copyright © 2018 深圳中电国际信息科技有限公司版权所有
 */
@Controller
public class LoginController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger (BaseController.class);

    @RequestMapping({"/","/index","/home","/login"})
    public String loginPage () {
        return "login";
    }

    @RequestMapping("/manager-index")
    public String indexPage() {
        return "manager-index";
    }

    /**
     * 公用的页面 跳转请求处理方法  /--/redirect?redirect=页面地址
     * @param redirect
     * @param mode
     * @param request
     * @return
     */
    @RequestMapping("/redirect")
    public String redirect(String redirect, Model mode, HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;

        Map<String, String> map = MapUtil.mapTransfer(request.getParameterMap());
        Set<String> set = map.keySet();
        if (!set.isEmpty()) {
            Iterator<String> iter = set.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                mode.addAttribute(key, map.get(key));
            }
        }
        mode.addAttribute("basePath", basePath);
        mode.addAttribute("jsVersion", ProjectUtil.getJsVersion());
        return redirect;
    }

    /**
     *
     * 方法用途: <br>
     * 实现步骤: <br>
     *
     * @param sClazz
     * @return
     */
    @ResponseBody
    @RequestMapping("/getEnum")
    public String getEnum(String sClazz) {
        List<KeyVo> list = new ArrayList<KeyVo> ();
        try {
            Class<?> clazz = Class.forName(sClazz);
            Method values;
            values = clazz.getMethod("values");
            Method getValue = clazz.getMethod("value");
            Method getText = clazz.getMethod("text");
            Object[] objs = (Object[]) values.invoke(null, null);
            for (Object obj : objs) {
                KeyVo key = new KeyVo();
                key.setText(getText.invoke(obj));
                key.setValue(getValue.invoke(obj));
                list.add(key);
            }
        } catch (Exception e) {
            logger.error("BaseController出错", e);
            KeyVo key = new KeyVo();
            key.setText("程序出错");
            key.setValue(null);
            list.add(key);
        }
        return JSONUtils.valueToString (list);
    }

}
