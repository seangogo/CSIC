package com.easyRepair.api;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.CouponListModule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.CouponComService;
import com.easyRepair.service.CouponQualifiedService;
import com.easyRepair.service.CouponService;
import com.easyRepair.service.UserCouponService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.CouponQualified;
import com.easyRepair.tabEntity.UserCoupon;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 Created by sean on 2017/1/3. */
@Api(value = "/coupon", description = "优惠券相关接口")
@RestController
@RequestMapping(value = {"/api/coupon"})
public class CouponApiController {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponComService couponComService;
    @Autowired
    private CouponQualifiedService couponQualifiedService;
    @Autowired
    private UserCouponService userCouponService;
    
    /*领券中心*/
    @RequestMapping(value = "centre/coupon")
    @ApiOperation(notes = "领券中心优惠券分页", httpMethod = "POST", value = "领券中心")
    public void couponPage(@ApiParam(name = "pageNum", required = true, value = "页码")
                            @RequestParam(value = "pageNum") Integer pageNum, HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        if(userSessionModul.getType().equals("1")) {
            WebUtil.printApi(response, new Result(false).msg("工程师不可查看优惠券!").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
       /*查询可以领取的所有优惠券*/
        Page<Coupon> couponPage = couponService.findpageByTime(new PageRequest(pageNum - 1,10));
        List<CouponListModule> couponListModuleList = new ArrayList<CouponListModule>();
        for(Coupon coupon : couponPage.getContent()) {
            couponListModuleList.add(CouponListModule.getCoupon(coupon));
        }
        WebUtil.printApi(response, new Result(true).data(DataTableFactory.fittingPojo(couponPage, couponListModuleList)));
    }
    
    /*领取优惠卷*/
    @RequestMapping(value = "dole/{id}/coupon")
    @ApiOperation(notes = "领取优惠卷", httpMethod = "POST", value = "领取优惠卷")
    public void getPage(@ApiParam(name = "id", value = "优惠卷ID")
                        @PathVariable(value = "id") Long id, HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Coupon coupon = couponService.getById(id);
        /*验证优惠卷数量*/
        if(coupon.getNumber() == 0) {
            WebUtil.printApi(response, new Result(false).msg("优惠卷数量不足"));
            return;
        }
        /*根据用户id和优惠券id查询领取次数*/
        int num = userCouponService.findCountByUserId(userSessionModul.getId(), coupon);
        if(coupon.getFrequency() != - 1 && coupon.getFrequency() <= num) {
            WebUtil.printApi(response, new Result(false).msg("超过领取次数"));
            return;
        }
        
        /*领取资格有限制*/
        if(coupon.getCouponCom() != null) {
            /*获取订单优惠券领取资格*/
            List<CouponQualified> couponQualifiedList = couponQualifiedService.findByUser_IdAndCouponCom(userSessionModul.getId(), coupon.getCouponCom());
            if(couponQualifiedList != null && couponQualifiedList.size() > 0) {
                UserCoupon userCoupon = couponComService.doleCoupon(couponQualifiedList.get(0),userSessionModul.getId(), coupon);
                if(userCoupon==null){
                    WebUtil.printApi(response, new Result(false).msg("没有领取资格"));
                    return;
                }else {
                    WebUtil.printApi(response, new Result(true).msg("领取成功"));
                    return;
                }
            } else {
                WebUtil.printApi(response, new Result(false).msg("没有领取资格"));
                return;
            }
        }
        /*领取资格无限制*/
            UserCoupon userCoupon = couponComService.doleCouponAll(userSessionModul.getId(), coupon);
            if(userCoupon == null) {
                WebUtil.printApi(response, new Result(false).msg("服务器错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            }
            WebUtil.printApi(response, new Result(true).msg("领取成功"));
    }
    
    /*我的订单(用户)*/
    @RequestMapping("mine/coupon/user")
    @ApiOperation(notes = "我的优惠券分页,查询所有登录用户的优惠券", httpMethod = "GET", value = "我的优惠券")
    public void mineOrderUser(@ApiParam(name = "pageNum", required = true, value = "页码")
                              @RequestParam(value = "pageNum") Integer pageNum,
                              @ApiParam(name = "status", required = true, value = "优惠卷状态")
                              @RequestParam(value = "status") Integer status,
                              HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        WebUtil.printApi(response,new Result(true).data(couponService.page(userSessionModul.getId(),
                status,new PageRequest(pageNum - 1, 10))));
    }
    
    
    
}
