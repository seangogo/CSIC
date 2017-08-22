package com.easyRepair.service.commons;


import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sean on 14-10-21.
 */
public class DataTableFactory {

    public static Map<String, Object> fitting(Page<?> page) {
        List<?> list = page.getContent();
        boolean isLast = page.isLast();
        boolean isFirst = page.isFirst();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", list);
        map.put("isLast", isLast);
        map.put("isFirst", isFirst);
        return map;
    }

    public static Map<String, Object> fittingPojo(Page<?> page,List<?> list) {
        boolean isLast = page.isLast();
        boolean isFirst = page.isFirst();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", list);
        map.put("isLast", isLast);
        map.put("isFirst", isFirst);
        return map;
    }
    public static Map<String,Object> fitting(Integer draw,Page<?> page){
        List<?> list = page.getContent();
        Map<String,Object>  map = new HashMap<String, Object>();
        map.put("data",list);
        map.put("draw",draw);
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return  map;
    }
    public static Map<String,Object> dataTableFitting(Page<?> page){
        Map<String,Object>  map = new HashMap<String, Object>();
        map.put("data",page.getContent());
        map.put("iTotalRecords",page.getTotalElements());
        map.put("iTotalDisplayRecords",page.getTotalElements());
        return  map;
    }
}
