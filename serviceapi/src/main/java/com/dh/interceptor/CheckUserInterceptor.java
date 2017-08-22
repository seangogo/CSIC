package com.dh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dh.commont.CommonButil;
import com.dh.entity.User;
import com.dh.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public class CheckUserInterceptor implements HandlerInterceptor {
    protected static final Logger LOG = LoggerFactory.getLogger(CheckUserInterceptor.class);
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        //检查使用户是否登录
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            response.getWriter().print(setReturnValue("用户登录超时，请重新登录", 0, null, null, "4001"));
            response.getWriter().close();
            return false;
        }


        if (1 == user.getIsLocked()) {
            response.getWriter().print(setReturnValue("您的账户已锁定，请联系系统管理员", 0, null, null, "4008"));
            response.getWriter().close();
            return false;
        }


        String requestDeviceId = request.getParameter("deviceId");
        if (CommonButil.isEmpty(requestDeviceId)) {
            response.getWriter().print(setReturnValue("请求参数错误，缺少设备ID", 0, null, null, null));
            response.getWriter().close();
            return false;
        }


        //判断当前请求的设备是否是当前登录的设备，若不是则返回错误
        String userDevice = userService.findUserDeviceId(user.getId());
        if (CommonButil.isEmpty(userDevice) || !userDevice.equals(requestDeviceId)) {
            String content = "你的帐号于" + CommonButil.getTimeStr(user.getLastLoginTime(), "HH:mm") + "在另一台设备登录。如非本人操作，则密码可能已泄露，建议及时修改密码。";
            response.getWriter().print(setReturnValue(content, 0, null, null, "4007"));
            response.getWriter().close();
            return false;
        }

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

    /**
     * 设置返回值
     *
     * @param m
     * @param c
     * @param o
     * @param e
     * @param r
     * @return
     */
    public String setReturnValue(String m, int c, Object o, Object e, String r) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("m", m);
        map.put("c", c);
        map.put("o", o);
        map.put("e", e);
        map.put("r", r);
        return JSONObject.toJSONString(map, SerializerFeature.WriteMapNullValue);
    }

}
