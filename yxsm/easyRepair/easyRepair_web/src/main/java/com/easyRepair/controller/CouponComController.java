package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.CouponComService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.CouponCom;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
@Controller
@RequestMapping("/couponCom")
public class CouponComController {

    @Autowired
    private CouponComService couponComService;

    @SystemControllerLog(description = "进入领券对象界面")
    @RequiresPermissions("couponCom:view")
    @RequestMapping("/list")
    public String toCouponCom( ){
        return "/couponCom/list";
    }

    @RequiresPermissions("couponCom:view")
    @RequestMapping(value = "/page")
    public void couponComPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<CouponCom> page = couponComService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "创建领券对象")
    @RequiresPermissions("couponCom:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void create(CouponCom couponCom, HttpServletResponse response) {
        try {
            couponCom.setCreateTime(new Date());
            couponComService.update(couponCom);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改领券对象")
    @RequiresPermissions("couponCom:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(CouponCom couponCom, HttpServletResponse response) {
        try {
            CouponCom couponCom2 = couponComService.getById(couponCom.getId());
            couponCom2.setType(couponCom.getType());
            couponCom2.setName(couponCom.getName());
            couponCom2.setStartTime(couponCom.getStartTime());
            couponCom2.setEndTime(couponCom.getEndTime());
            couponCom2.setPrice(couponCom.getPrice());
            couponCom2.setCut(couponCom.isCut());
            couponComService.update(couponCom2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除领券对象")
    @RequiresPermissions("couponCom:delete")
    @RequestMapping("/delete")
    public void deleteCouponCom(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            List<CouponCom> couponComs = couponComService.findByIds(arrayId);
            for (CouponCom couponCom : couponComs) {
                couponCom.setCut(true);
                couponComService.update(couponCom);
            }
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


}
