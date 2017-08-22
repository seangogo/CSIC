package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.DiscountService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Discount;
import com.easyRepair.tabEntity.Integrals;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/3.
 */
@Controller
@RequestMapping("/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    //@SystemControllerLog(description = "新增领券资格")
    @RequiresPermissions("discount:view")
    @RequestMapping(value = "/list")
    public String list() {
        return "discount/list";
    }

    @SystemControllerLog(description = "查看积分和基础参数列表")
    @RequiresPermissions("discount:view")
    @RequestMapping(value = "/page")
    public void integralsPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<Discount> page = discountService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SystemControllerLog(description = "修改积分和基础参数")
    @RequiresPermissions("discount:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(Discount discount, HttpServletResponse response) {
        try {
            Discount discount2 = discountService.getById(discount.getId());
            discount2.setNumOne(discount.getNumOne());
            discount2.setUpdateTime(new Date());
            //discount2.setNumTwo(discount.getNumTwo());
            //discount2.setType(discount.getType());
            discountService.update(discount2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }
}
