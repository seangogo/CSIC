package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.*;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.CouponCom;
import com.easyRepair.tabEntity.ServiceType;
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
 * Created by WangJingYu on 2017/2/15.
 */
@Controller
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponComService couponComService;

    @Autowired
    private CouponQualifiedService couponQualifiedService;

    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private ServiceAppointedTypeService serviceAppointedTypeService;


    @SystemControllerLog(description = "进入优惠券界面")
    @RequiresPermissions("coupon:view")
    @RequestMapping("/list")
    public String toCoupon( ){
        return "/coupon/list";
    }


    @RequiresPermissions("coupon:view")
    @RequestMapping(value = "/page")
    public void couponPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<Coupon> page = couponService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));

            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "新增优惠券")
    @RequiresPermissions("coupon:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void createCoupon(Coupon coupon, Long serviceTypeId, Long couponComId, HttpServletResponse response) {
        try {
            CouponCom couponCom = couponComService.getById(couponComId);
            ServiceType serviceType = serviceTypeService.getById(serviceTypeId);
            //long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            //List<ServiceType> serviceTypes = serviceTypeService.getByIds(arrayId);
            coupon.setServiceType(serviceType);
            coupon.setCouponCom(couponCom);
            coupon.setCreateTime(new Date());
            coupon.setUpdateTime(new Date());
            couponService.update(coupon);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改优惠券")
    @RequiresPermissions("coupon:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateCoupon(Coupon coupon, Long serviceTypeId, Long couponComId, HttpServletResponse response) {
        try {
            Coupon coupon2=couponService.getById(coupon.getId());
            coupon2.setName(coupon.getName());
            coupon2.setDeduction(coupon.getDeduction());
            coupon2.setNumber(coupon.getNumber());
            coupon2.setServiceType(serviceTypeService.getById(serviceTypeId));
            coupon2.setPrice(coupon.getPrice());
            coupon2.setStartTime(coupon.getStartTime());
            coupon2.setEndTime(coupon.getEndTime());
            coupon2.setGetStartTime(coupon.getGetStartTime());
            coupon2.setGetEndTime(coupon.getGetEndTime());
            coupon2.setCouponCom(couponComService.getById(couponComId));
            coupon2.setFrequency(coupon.getFrequency());
            coupon2.setCut(coupon.isCut());
            coupon2.setPush(coupon.isPush());
            coupon2.setUpdateTime(new Date());

            couponService.update(coupon2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除优惠券")
    @RequiresPermissions("coupon:delete")
    @RequestMapping("/delete")
    public void deleteCoupon(HttpServletResponse response,String ids){
        /*try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            couponService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!请先删除其他关联数据"));
        }*/
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            List<Coupon> coupons = couponService.getByIds(arrayId);
            for (Coupon coupon : coupons) {
                coupon.setCut(true);
                couponService.update(coupon);
            }
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /*@RequiresPermissions("coupon:view")
    @RequestMapping("/allServiceType")
    @ResponseBody
    public List<TreeNode> allServiceType() {
        List<TreeNode> serviceTypes = serviceTypeService.findServiceTypeTree();
        return serviceTypes;
    }*/
    @RequiresPermissions("coupon:view")
    @RequestMapping("/allServiceType")
    @ResponseBody
    public List<ServiceType> allServiceType() {
        List<ServiceType> serviceTypes = serviceTypeService.findAll();
        return serviceTypes;
    }

    @RequiresPermissions("coupon:view")
    @RequestMapping("/allCouponCom")
    @ResponseBody
    public List<CouponCom> allCouponCom() {
        List<CouponCom> couponComs = couponComService.findAll();
        return couponComs;
    }

    @RequiresPermissions("coupon:view")
    @RequestMapping("/findServiceType")
    @ResponseBody
    public ServiceType findServiceType(Long id) {
        Coupon coupon = couponService.getById(id);
        ServiceType serviceType = coupon.getServiceType();
        return serviceType;
    }

    @RequiresPermissions("coupon:view")
    @RequestMapping("/findCouponCom")
    @ResponseBody
    public CouponCom findCouponCom(Long id) {
        Coupon coupon = couponService.getById(id);
        CouponCom couponCom = coupon.getCouponCom();
        return couponCom;
    }

    @RequiresPermissions("coupon:update")
    @RequestMapping("/updatePush")
    public void updatePush(Long id, boolean push, HttpServletResponse response) {
        Coupon coupon = couponService.getById(id);
        coupon.setPush(push);
        couponService.update(coupon);
        WebUtil.print(response, new Result(true).msg("推送成功"));
    }
}
