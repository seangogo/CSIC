package com.easyRepair.api.interceptor;

import com.easyRepair.commons.WebUtil;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import common.utils.Md5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class RequestInterceptor implements HandlerInterceptor {
    protected static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Map<String, String> map = getParams(request);
        //判断是否是开发模式
        if ("1".equals(map.get("debug"))) {
            return true;
        }
        String requestSign = map.get("sign");
        if (StringUtils.isEmpty(map.get("deviceId").toString()) || StringUtils.isEmpty(requestSign)) {
            WebUtil.printApi(response,new Result(false).msg("请求参数错误，缺少设备ID").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return false;
        }
        if (!StringUtils.isEmpty(map.get("photos").toString())) {
            map.remove("imgStrs");
        }
        map.remove("sign");
        if (!requestSign.equals(creatSign(map))) {
            WebUtil.printApi(response,new Result(false).msg("请求参数签名错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
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

    private Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if(paramValues.length == 1) {
                String paramValue = paramValues[0];
                if(paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }


    public String creatSign(Map<String, String> paramMap) throws UnsupportedEncodingException {
        Set<String> keySet = paramMap.keySet();
        List<String> keyList = new ArrayList<>(keySet);
        Collections.sort(keyList);
        StringBuffer sb = new StringBuffer();
        for (String k : keyList) {
            String v = paramMap.get(k);
            if (!StringUtils.isEmpty(k) && !StringUtils.isEmpty(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        try {
            String url=URLEncoder.encode(sb.toString(), "UTF-8");
            return Md5Util.encode(url).toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
