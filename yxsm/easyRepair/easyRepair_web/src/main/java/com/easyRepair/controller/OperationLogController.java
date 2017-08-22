package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.OperationLogService;
import com.easyRepair.service.OrderService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.OperationLog;
import com.easyRepair.tabEntity.User;
import common.core.bean.PageParam;
import common.utils.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sean on 2017/3/14.
 */
@Controller
@RequestMapping("/operationLog")
public class OperationLogController {
    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping("list")
    public String list() {
        return "operationLog/list";
    }

    /**
     * 日志分页
     * @param request
     * @param start
     * @param length
     */
    @RequestMapping(value = "page",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getUser(HttpServletRequest request, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<OperationLog> page = operationLogService.page(searchParams,new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            return DataTableFactory.dataTableFitting(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
