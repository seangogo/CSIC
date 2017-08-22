package com.dh.web.api;

import com.dh.configure.Consts;
import com.dh.web.api.editor.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

public class BaseApiController extends Consts {
    public static Map<String, Object> emptyData = null;

    static {
        emptyData = new HashMap<String, Object>();
        emptyData.put("data", new ArrayList());
        emptyData.put("iTotalRecords", 0);
        emptyData.put("iTotalDisplayRecords", 0);
    }

    /**
     * 结构返回值
     *
     * @param m 返回消息
     * @param c 请求成功/失败
     * @param o 返回对象
     * @param e 返回其他内容
     * @param r 错误状态码
     * @return Map<String,Object>
     */
    public static Map<String, Object> setReturnValue(String m, int c, Object o, Object e, String r) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("m", m);
        map.put("c", c);
        map.put("o", o);
        map.put("e", e);
        map.put("r", r);
        return map;
    }

    /**
     * 结构返回值
     *
     * @param m 返回消息
     * @param c 请求成功/失败
     * @param o 返回对象
     * @param e 返回其他内容
     * @return Map<String,Object>
     */
    public static Map<String, Object> setReturnValue(String m, int c, Object o, Object e) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("m", m);
        map.put("c", c);
        map.put("o", o);
        map.put("e", e);
        map.put("r", null);
        return map;
    }

    /**
     * 结构返回值
     *
     * @param m 返回消息
     * @param c 请求成功/失败
     * @param o 返回对象
     * @return Map<String,Object>
     */
    public static Map<String, Object> setReturnValue(String m, int c, Object o) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("m", m);
        map.put("c", c);
        map.put("o", o);
        map.put("e", null);
        map.put("r", null);
        return map;
    }

    /**
     * 结构返回值
     *
     * @param m 返回消息
     * @param c 请求成功/失败
     * @return Map<String,Object>
     */
    public static Map<String, Object> setReturnValue(String m, int c) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("m", m);
        map.put("c", c);
        map.put("o", null);
        map.put("e", null);
        map.put("r", null);
        return map;
    }

    /*from Sean create Util*/
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(MultipartFile.class, new CustomFileEditor());
        binder.registerCustomEditor(Double.class, new CustomDoubleEditor());
        binder.registerCustomEditor(Float.class, new CustomFloatEditor());
        binder.registerCustomEditor(Integer.class, new CustomIntegerEditor());
        binder.registerCustomEditor(Long.class, new CustomLongEditor());
        binder.registerCustomEditor(Date.class, new CustomDateEditor());
    }

    public Map<String, Object> createMap(String key, Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, obj);
        return map;
    }

    public Integer getPageNum(Integer start, Integer length) {
        if (start == null) {
            start = 0;
        }
        if (length == null) {
            length = 10;
        }

        int pageNum = (start / length) + 1;
        return pageNum;
    }

}
