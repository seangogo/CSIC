package com.easyRepair.controller.systemController;

import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.Resource;
import common.utils.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 Created by sean on 2016/11/8. */
@Controller
@RequestMapping("/")
public class loginController{
    @RequestMapping("/backendLogin")
    public String backendLogin() {
        return "backendLogin";
    }
    
    @RequestMapping("/login/backend")
    public String loginBackend(HttpServletRequest request, UsernamePasswordToken token) {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("shiroLoginFailure:"+exceptionClassName);
        /*if (request.getParameter("rememberMe") == "true") {
            token.setRememberMe(true);
        }*/
        if (exceptionClassName != null && exceptionClassName.contains("UnknownAccountException")) {
            request.setAttribute("login", "账号不存在");
        } else if (exceptionClassName != null && exceptionClassName.contains("IncorrectCredentialsException")) {
            request.setAttribute("password", "密码错误");
        }
        return "/backendLogin";
    }
    
    @RequestMapping(value = {"index","/"})
    public String index() {
        return "/index";
    }

    /*@RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            subject.logout();
        }
        return "/backendLogin";
    }*/
    @RequestMapping("/indexContent")
    public String indexContent() {
        return "/indexContent";
    }
    
}
