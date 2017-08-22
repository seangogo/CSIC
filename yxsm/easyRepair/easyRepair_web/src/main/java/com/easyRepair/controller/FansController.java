package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.FansService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Fans;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by WJY on 2017/2/16.
 */
@Controller
@RequestMapping("/fans")
public class FansController {
    @Autowired
    private FansService fansService;

    @RequiresPermissions("fans:view")
    @RequestMapping("/list")
    public String list(){
        return  "fans/list";
    }

    @SystemControllerLog(description = "查看关注者列表")
    @RequiresPermissions("fans:view")
    @RequestMapping("page")
    public void typePage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<Fans> page = fansService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
