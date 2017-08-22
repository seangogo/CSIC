package com.dh.commont;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sean on 2014-12-15.
 */
public class APIFactory {

    /**
     * json转换公共类
     *
     * @param page 分页数据
     * @return
     */
    public static Map<String, Object> fitting(Page<?> page) {
        List<?> list = page.getContent();
        // data中的page
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("totalElements", page.getTotalElements());
        pageMap.put("totalPages", page.getTotalPages());
        pageMap.put("pageSize", page.getSize());//页数
        pageMap.put("numberOfElements", page.getNumberOfElements());//当前页条数
        pageMap.put("currentPage", page.getNumber() + 1);//当前页
        pageMap.put("isLastPage", page.isLast());//是否是退后一夜

        // data中的list
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("o", list);
        dataMap.put("e", pageMap);
        return dataMap;
    }

    /**
     * json转换公共类
     *
     * @param page 分页数据
     * @return
     */
    public static Map<String, Object> fitting(Page<?> page, List<?> list) {
        // data中的page
        Map<String, Object> pageMap = new HashMap<String, Object>();
        if (null == list || list.size() == 0) {
            pageMap.put("totalNum", 0);
            pageMap.put("totalPage", 0);
        } else {
            pageMap.put("totalNum", page.getTotalElements());
            pageMap.put("totalPage", page.getTotalPages());
        }
        pageMap.put("currentPage", page.getNumber() + 1);

        // data中的list
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("list", list);
        dataMap.put("page", pageMap);
        return dataMap;
    }

    /**
     * json转换公共类
     * <p/>
     * 合并特殊分页数据
     *
     * @return
     */
    public static Map<String, Object> fittingPlus(Page<?> page1, Page<?> page2, Integer errorCount, List<?> list) {
        // data中的page
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("totalNum", page1.getTotalElements() + page2.getTotalElements() - errorCount);
        pageMap.put("totalPage", page1.getTotalPages() + page2.getTotalPages());
        pageMap.put("currentPage", page1.getNumber() + 1);

        // data中的list
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("list", list);
        dataMap.put("page", pageMap);
        return dataMap;
    }

    public static Map<String, Object> fitting(List list) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("list", list);
        return dataMap;
    }


}
