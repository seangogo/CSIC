package com.dh.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dh.commont.CommonButil;
import com.dh.commont.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class RequestInterceptor implements HandlerInterceptor {
    /**
     * 是否是debug模式
     */
    private static Boolean isDebug = false;
    protected static final Logger LOG = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Map<String, String> map = getParams(request);
        isDebug = "1".equals(map.get("debug"));
        //判断是否是开发模式
        if (isDebug) {
            return true;
        }
        String requestSign = map.get("sign");
        if (CommonButil.isEmpty(map.get("deviceId")) || CommonButil.isEmpty(requestSign)) {
            response.getWriter().print(setReturnValue("请求参数错误，缺少设备ID", 0, null, null, null));
            response.getWriter().close();
            return false;
        }
        if (!CommonButil.isEmpty(map.get("imgStrs"))) {
            map.remove("imgStrs");
        }
        map.remove("sign");
        String sign=creatSign(map);
        if (!requestSign.equals(sign)) {
            response.getWriter().print(setReturnValue("请求参数签名错误", 0, null, null, null));
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

    private Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }


    public String creatSign(Map<String, String> paramMap) {
        Set<String> keySet = paramMap.keySet();
        List<String> keyList = new ArrayList<String>(keySet);
        Collections.sort(keyList);
        StringBuffer sb = new StringBuffer();
        for (String k : keyList) {
            String v = paramMap.get(k);
            if (!CommonButil.isEmpty(k) && !CommonButil.isEmpty(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        try {
            return MD5.encode(URLEncoder.encode(sb.toString(), "UTF-8")).toLowerCase();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
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
