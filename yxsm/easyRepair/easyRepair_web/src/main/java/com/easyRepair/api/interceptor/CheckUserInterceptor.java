package com.easyRepair.api.interceptor;


import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.UserSessionModul;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUserInterceptor extends HandlerInterceptorAdapter {
    protected static final Logger LOG = LoggerFactory.getLogger(CheckUserInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //检查使用户是否登录
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        if(null == userSessionModul) {
            WebUtil.printJson(response, new Result(false).msg("用户登录超时，请重新登录").data(EASY_ERROR_CODE.ERROR_CODE_0026));
            return false;
        }

       /* if (userSessionModul.isLocked()) {
            WebUtil.printJson(response,new Result(false).msg("您的账户已锁定，请联系系统管理员").data(EASY_ERROR_CODE.ERROR_CODE_0027));
            return false;
        }
        
        String requestDeviceId = request.getParameter("deviceId");
        if (StringUtils.isEmpty(requestDeviceId)) {
            WebUtil.printJson(response,new Result(false).msg("请求参数错误，缺少设备ID").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return false;
        }
        
        //判断当前请求的设备是否是当前登录的设备，若不是则返回错误
       // String userDevice = userLoginInfoService.findDeviceIdByUser(UserSessionModul);
        if (StringUtils.isEmpty(userSessionModul.getDeviceId()) || !userSessionModul.getDeviceId().equals(requestDeviceId)) {
            WebUtil.printJson(response,new Result(false).msg("你的帐号于" + userSessionModul.getLastLoginTime() + "在另一台设备登录。如非本人操作，则密码可能已泄露，建议及时修改密码。").data(EASY_ERROR_CODE.ERROR_CODE_0028));
            return false;
        }*/
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        response.setContentType("text/html; charset=UTF-8");
    }
}
