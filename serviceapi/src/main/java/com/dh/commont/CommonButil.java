package com.dh.commont;

import org.springframework.data.domain.Page;
import org.springframework.web.context.ContextLoaderListener;
import org.springside.modules.utils.Clock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CommonButil {

    private static Clock clock = Clock.DEFAULT;

    /**
     * 设置session
     *
     * @param str
     * @param obj
     */
    public static void setSession(String str, Object obj) {
        ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().setAttribute(str, obj);
    }

    /**
     * 获取session
     *
     * @param str
     * @return
     */
    public static Object getSession(String str) {
        return ContextLoaderListener.getCurrentWebApplicationContext().getServletContext().getAttribute(str);
    }

    /**
     * 判断变量是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s);
    }

    public static boolean isEmpty(Object[] s) {
        return null == s || s.length < 1;
    }

    public static boolean isEmpty(List<?> s) {
        return null == s || s.isEmpty() || s.size() < 1;
    }

    public static boolean isEmpty(Object s) {
        return null == s;
    }

    public static boolean isEmpty(Map<?, ?> s) {
        return null == s || s.isEmpty() || s.size() < 1 || s.keySet().size() < 1;
    }

    /**
     * 发送短信接口
     *
     * @param loginName
     * @return
     */
    public static boolean sendMessage(String loginName, String content) {
        System.out.println("loginName:" + loginName + " content:" + content);
        return true;
    }

    /**
     * 获取指定长度的随机数
     *
     * @param length 长度
     * @return
     */
    public static String getRandomNum(int length) {
        String num = "";
        for (int i = 1; i < length; i++) {
            num += "0";
        }
        long a = Long.parseLong(9 + num);
        long b = Long.parseLong(1 + num);
        return Math.round(Math.random() * a + b) + "";
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowTime() {
        return clock.getCurrentDate();
    }

    /**
     * 获取当前时间字符串
     *
     * @return
     */
    public static String getNowTimeStr() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(getNowTime());
    }

    /**
     * 获取当前时间字符串
     *
     * @param str 时间格式
     * @return
     */
    public static String getNowTimeStr(String str) {
        return new SimpleDateFormat(str).format(getNowTime());
    }

    /**
     * 获取当前字符串
     *
     * @return
     */
    public static String getTimeStr(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 获取时间字符串
     *
     * @return
     */
    public static String getTimeStr(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }


    /**
     * 根据框架Page对象 转化为 自己的 pageInfo对象
     *
     * @param result
     * @param currentPage
     * @return
     */
    public static PageInfo setPageInfo(Page<?> result, int currentPage) {
        PageInfo info = new PageInfo();
        info.setCurrentPage(currentPage);
        info.setPageSize(result.getSize());
        info.setNumberOfElements(result.getNumberOfElements());
        info.setTotalElements(result.getTotalElements());
        info.setTotalPages(result.getTotalPages());
        info.setIsLastPage(result.isLast());
        return info;
    }

}
