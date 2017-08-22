package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.OrderService;
import com.easyRepair.service.ResourceService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.Resource;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/24.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("orderlist")
    public String orderlist() {
        return "order/list";
    }
    
	@RequestMapping("detail")
    public String detail() {
        return "order/detail";
    }
	
    @RequiresPermissions("order:view")
    @RequestMapping("page")
    @ResponseBody
    public Map<String, Object> page(HttpServletRequest request,Integer start, Integer length) {
        try {
            Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
            int pageNum = PageParam.getPageNum(start, length);//分页
            Object[] sort = WebUtil.getSortParameters(request);//排序
            Page<Order> page = orderService.page(searchParams,new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            return  DataTableFactory.dataTableFitting(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
