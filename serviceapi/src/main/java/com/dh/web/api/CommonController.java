package com.dh.web.api;

import com.dh.web.api.editor.*;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sean on 2014/12/10.
 */
public class CommonController {


    public static Map<String, Object> emptyData = null;

    static {
        emptyData = new HashMap<String, Object>();
        emptyData.put("data", new ArrayList());
        emptyData.put("iTotalRecords", 0);
        emptyData.put("iTotalDisplayRecords", 0);
    }


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
