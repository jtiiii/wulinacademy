package com.funeral.wulinconsole.Controller;

import com.funeral.wulinapi.Enum.SessionCodeEnum;
import com.funeral.wulinapi.model.user.AdminUserModel;
import com.funeral.wulinapi.vo.baseVo.KeyVo;
import com.funeral.wulincore.common.ProjectUtil;
import com.funeral.wulincore.service.user.IAdminUserService;
import com.funeral.wulincore.utils.MapUtil;
import com.funeral.wulincore.utils.WuLinStringUtils;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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

    @Resource
    private IAdminUserService adminUserService;

    @RequestMapping({"/","/index","/home","/login"})
    public String loginPage () {
        return "login";
    }

    @RequestMapping("/permission")
    public String indexPage() {
        return "manager/index";
    }


    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/signUp")
    public String logout() {
        session.invalidate();
        return "redirect:login";
    }

    /*用户登陆*/
    @RequestMapping(value="/sign_in",method= RequestMethod.POST)
    @ResponseBody
    public String logined(AdminUserModel params,String code){
        if(params != null){
            if(StringUtils.isNotEmpty(params.getAccount()) && StringUtils.isNotEmpty(params.getPassword())){
                params.setPassword(WuLinStringUtils.md5Base64(params.getPassword()));
                AdminUserModel user = this.adminUserService.getLogin(params);
                //获取验证码与验证码的次数
                if(StringUtils.isNotEmpty(code)){
                    //获取会话的验证码
                    String sessionCode = (String) request.getSession().getAttribute(SessionCodeEnum.KAPTCHA_SESSION_KEY.toString());
                    if(StringUtils.isNotEmpty(sessionCode) && !sessionCode.equalsIgnoreCase(code)){
                        return "error_code";
                    }
                }
                if(user != null){
                    if(user.getForbiden() == 1){
                        session.setAttribute(SessionCodeEnum.SESSION_USER.getVal(), user);
                        session.setAttribute(SessionCodeEnum.SESSION_USER_NAME.getVal(), user.getUserName());
                        session.setAttribute(SessionCodeEnum.SESSION_USER_ID.getVal(), user.getId());
                        return "success";
                    }else{
                        return "forbid";
                    }
                }else{
                    return "fail";
                }
            }else{
                return "null";//提示输入用账号与密码
            }
        }else{
            return "error";
        }
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
