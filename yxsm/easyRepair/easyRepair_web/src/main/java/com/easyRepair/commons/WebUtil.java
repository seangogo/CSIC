package com.easyRepair.commons;

import common.core.Constant;
import common.utils.JsonUtil;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by wangbin on 14-10-17.
 */
public class WebUtil {


    public static void print(HttpServletResponse response, Object data){
        printJson(response, data);
    }

    public  static void printJson(HttpServletResponse response, Object data){
        try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding(Constant.ENCODING); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtil.obj2Json(data)); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public  static void printJson(HttpServletResponse response, String data){
        try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding(Constant.ENCODING); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(data); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void printApi(HttpServletResponse response, Object data){
        try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding(Constant.ENCODING); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtil.obj2ApiJson(data)); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void printApi(HttpServletResponse response, String data){
        try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding(Constant.ENCODING); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();

            writer.write(JsonUtil.filterJson(data)); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
     *
     * 返回的结果的Parameter名已去除前缀.
     */
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while ((paramNames != null) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if ((values == null) || (values.length == 0)) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    public static Object[] getSortParameters(ServletRequest request) {
        String order = request.getParameter("order[0][column]");//排序的列号
        String orderDir = request.getParameter("order[0][dir]");//排序的顺序asc or desc
        String orderColumn  = request.getParameter("columns[" + order + "][data]");//排序的字段名
        if (StringUtils.isEmpty(orderDir)||StringUtils.isEmpty(orderColumn)) {
           orderColumn="id";
        }
        Object[] sort = {orderColumn, "desc".equals(orderDir) ? Sort.Direction.DESC : Sort.Direction.ASC};
        return sort;
    }

}
