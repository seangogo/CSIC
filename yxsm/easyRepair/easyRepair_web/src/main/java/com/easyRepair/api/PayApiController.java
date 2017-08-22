package com.easyRepair.api;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.OrderModule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.OrderService;
import com.easyRepair.service.PayLogService;
import com.easyRepair.service.UserService;
import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.PayLog;
import com.easyRepair.tabEntity.User;
import common.core.EASY_ERROR_CODE;
import common.core.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 Created by sean on 2017/1/12. */
@Api(value = "/pay", description = "支付的相关接口")
@RestController
@RequestMapping(value = "/api/pay")
public class PayApiController {
    
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PayLogService payLogService;
    
    //用户支付订单金额给平台
    @RequestMapping("pay/{id}/order")
    @ApiOperation(notes = "我的钱包支付订单(维修费和上门费)", httpMethod = "POST", value = "支付保证金")
    public void payOrderPrice(@ApiParam(name = "id", value = "订单ID")
                              @PathVariable(value = "id") Long id,
                              HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.findByIdAndUser_Id(id, userSessionModul.getId());
        if(order == null||order.getStatus()!=2){
            WebUtil.printApi(response, new Result(false).msg("订单不存在").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        float money=userSessionModul.getMoney();
        User user=userService.getById(userSessionModul.getId());
        if(user.getUserInfo().getMoney()!=money||money<order.getRealpay()){
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        //查询账户剩余金额是否足够支付订单金额
        order =orderService.payPrice(order);//订单支付业务
        OrderModule orderModule = ApiBeanUtils.getOrderdetailModel(order, userSessionModul.getType().equals("0") ? order.getRepair() : order.getUser());
        WebUtil.printApi(response, new Result(true).msg("支付成功！").data(orderModule));
    }
    
    /*用户支付确认支付订单费用*/
    @RequestMapping("confirmPay/{type}/{orderId}/order")
    @ApiOperation(notes = "确认支付订单费用(0=维修费,1=上门费)", httpMethod = "POST", value = "确认支付")
    public void confirmPay(@ApiParam(name = "orderId", value = "订单ID")
                              @PathVariable(value = "orderId") Long orderId,
                              @ApiParam(name = "type", value = "支付类型(0:全额 1:上门费)")
                              @PathVariable(value = "type") Integer type,
                              HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.findByIdAndUser_Id(orderId, userSessionModul.getId());
        if(order == null||order.getStatus()!=3){
            WebUtil.printApi(response, new Result(false).msg("订单不存在").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
     /*   PayLog payLog=payLogService.findByOrderAndPayMode(order,order.getOrderInfo().getPayMode());*//*获取支付日志*//*
        if(payLog == null||!payLog.getStatus()){
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }*/
        /*确认支付操作*/
        PayLog payLogBack =orderService.confirmPay(order,type==0);//订单支付业务
        if(payLogBack==null&&!payLogBack.getStatus()){
            WebUtil.printApi(response, new Result(false).msg("支付失败").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        WebUtil.printApi(response, new Result(true).msg("支付成功！"));
    }
    //退款
    @RequestMapping("refund/{id}/order")
    @ApiOperation(notes = "用户端退款",httpMethod = "POST", value = "订单退款")
    public void refund(@ApiParam(name = "id", value = "订单ID")
                              @PathVariable(value = "id") Long id,
                              HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.findByIdAndUser_Id(id, userSessionModul.getId());
        if(order == null||order.getStatus()!=3){
            WebUtil.printApi(response, new Result(false).msg("订单不存在").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        PayLog payLog=payLogService.findByOrderAndPayMode(order,order.getOrderInfo().getPayMode());/*获取支付日志*/
        if(payLog == null||!payLog.getStatus()){
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        /*账户支付*/
        PayLog payLogBack = null;
        if(payLog.getPayMode().equals(2)){
            payLogBack=orderService.refund(order);
        }else if(payLog.getPayMode().equals(0)){
            //todo 微信
        }else if(payLog.getPayMode().equals(1)){
            //todo 支付宝
        }
        if(payLogBack==null||!payLogBack.getStatus()){
            WebUtil.printApi(response, new Result(false).msg("退款失败").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        OrderModule orderModule = ApiBeanUtils.getOrderdetailModel(order, userSessionModul.getType().equals("0") ? order.getRepair() : order.getUser());
        WebUtil.printApi(response, new Result(true).msg("退款成功").data(orderModule));
        
    }
}
