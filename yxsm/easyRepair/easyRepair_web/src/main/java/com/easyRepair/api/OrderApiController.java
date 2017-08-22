package com.easyRepair.api;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.ApiBeanUtils;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.OrderModule;
import com.easyRepair.pojo.RepairMatchingModule;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.*;
import com.easyRepair.tabEntity.*;
import common.core.EASY_ERROR_CODE;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import common.utils.jpush.SendPush;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/24. */
@Api(value = "/order", description = "关于订单操作的相关接口")
@RestController
@RequestMapping(value = "/api/order", produces = {"text/html;charset=UTF-8"})
public class OrderApiController {
    @Autowired
    private OrderService orderService;
    /*订单工程师匹配表*/
    @Autowired
    private AreasSearchService areasSearchService;
    /*用户表*/
    @Autowired
    private UserService userService;
    /*工程师详情*/
    @Autowired
    private UserRepairInfoService userRepairInfoService;
    /*实时坐标上传*/
    @Autowired
    private UserPositionService userPositionService;
    /*交易记录*/
    @Autowired
    private TradeInfoService tradeInfoService;
    /*评论*/
    @Autowired
    private CommentsService commentsService;
    /*订单请求*/
    @Autowired
    private RequestDateService requestDateService;

    /**
     客户端用户发布订单
     */
    @RequestMapping(value = "/put/order", produces = {"*/*"}, method = RequestMethod.POST)
    @ApiOperation(notes = "成功参数：{\"appointmentTime\": \"2016-11-30T02:54:56.715Z\",\"areasSearch\":{\n" +
            "\"lat\": 12.333, \"lng\": 12.333,\"postCode\": \"431700\"}, \"orderImages\": [{ \"url\": \"路径或流\"}],\"orderInfo\": {\"address\": \"china\",\n" +
            " \"consignee\": \"seangoog\",\"mobile\": \"13997956178\",\"score\": 0},\"remark\": \"发布订单所填备注\",\"serviceType\":{\"id\": 1}}", httpMethod = "POST", value = "发布订单")
    public void put(HttpServletRequest request,
                    @RequestParam
                    @ApiParam(name = "orderStr", required = true, value = "发布订单字段" +
                            "appointmentTime:预约时间,areasSearch.lat=纬度;areasSearch.lng=精度;areasSearch.postCode=邮编;" +
                            "coupon.id=所使用的优惠券;orderImages.url:图片上传的路径或流;orderInfo.address:上门地址;orderInfo.consignee:联系人" +
                            "orderInfo.mobile=联系电话;orderInfo.score=订单所使用的积分;remark=发布订单所填备注;serviceType.id=所选服务类型的ID,如果需要匹配工程师" +
                            "将要匹配的工程师ID放入order.repair.id=匹配工程师的ID") String orderStr,
                    HttpServletResponse response) {
        /*将json字符串转为对象*/
        Order order = JsonUtil.json2Obj(orderStr, Order.class);
        Map<String,Object> map=orderService.putOrder(request,order);
        int result = (int)map.get("result");
        switch(result) {
            case 0:
                WebUtil.print(response, new Result(true).msg("发布成功，等待工程师抢单").data(map.get("data")));
                break;
            case 1:
                WebUtil.print(response, new Result(false).msg("工程师不可发单").data(EASY_ERROR_CODE.ERROR_CODE_0032));
                break;
            case 2:
                WebUtil.print(response, new Result(false).msg("发单服务类型不可为空").data(EASY_ERROR_CODE.ERROR_CODE_0033));
                break;
            case 3:
                WebUtil.print(response, new Result(false).msg("积分有误").data(EASY_ERROR_CODE.ERROR_CODE_0034));
                break;
            case 4:
                WebUtil.print(response, new Result(false).msg("参数有误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
                break;
            case 5:
                WebUtil.printApi(response, new Result(false).msg("该优惠券不存在").data(EASY_ERROR_CODE.ERROR_CODE_0035));
                break;
            case 6:
                WebUtil.printApi(response, new Result(false).msg("该订单不能使用此优惠券").data(EASY_ERROR_CODE.ERROR_CODE_0036));
                break;
            case 7:
                WebUtil.print(response, new Result(true).msg("发布成功，等待工程师确认接单"));
                break;
            default:
                WebUtil.printApi(response, new Result(false).msg("未知错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
                break;
        }
    }
    
    /*传递用户当前经纬度匹配相应同一订单服务的工程师*/
    @RequestMapping("matching/repair")
    @ResponseBody
    @ApiOperation(notes = "传递用户当前经纬度匹配相应同一订单服务的工程师:测试参数lng=114.2873066960,lat=30.4663842867,serviceId=2", httpMethod = "POST", value = "匹配工程师")
    public void matchingRepair(@ApiParam(name = "lng", value = "经度")
                               @RequestParam(value = "lng") Double lng,
                               @ApiParam(name = "lat", value = "纬度")
                               @RequestParam(value = "lat") Double lat,
                               @ApiParam(name = "serviceId", value = "服务类型Id")
                               @RequestParam(value = "serviceId") Long serviceId,
                               @ApiParam(name = "pageNum", value = "页码")
                               @RequestParam(value = "pageNum") Integer pageNum, HttpServletResponse response) {
        PageParam pageParam = new PageParam(pageNum);
        List<RepairMatchingModule> repairMatchingModuleList = areasSearchService.findByLngAndLatAndServiceTypeId(lng, lat, serviceId, pageParam.getStart(), pageParam.getLength());
        WebUtil.printApi(response, new Result(true).data(repairMatchingModuleList));
    }
    
    /*根据经纬度查询同一城市的推荐订单*/
    @RequestMapping("matching/order")
    @ApiOperation(notes = "传递工程师当前经纬度匹配相应同一订单服务的订单:测试参数lng=114.2873066960,lat=30.4663842867", httpMethod = "POST", value = "推荐订单")
    public void matchingOrder(@ApiParam(name = "lng", value = "经度")
                              @RequestParam(value = "lng") Double lng,
                              @ApiParam(name = "lat", value = "纬度")
                              @RequestParam(value = "lat") Double lat,
                              @ApiParam(name = "pageNum", value = "页码")
                              @RequestParam(value = "pageNum") Integer pageNum,
                              HttpServletResponse response) {
        PageParam pageParam = new PageParam(pageNum);
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        if(userSessionModul.getType().equals("0")) {
            WebUtil.printApi(response, new Result(false).msg("角色错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Map<String, Object> orderListMuduleMap = areasSearchService.findByLngAndLatAndServiceAreaId(lng, lat,userSessionModul.getId(), pageParam.getStart(), pageParam.getLength());
        WebUtil.printApi(response, new Result(true).data(orderListMuduleMap));
    }
    
    /*工程师确认接单*/
    @RequestMapping("confirm/{userId}/to/{orderId}")
    @ApiOperation(notes = "确认订单", httpMethod = "POST", value = "确认接单")
    public void confirmOrder(@ApiParam(name = "userId", value = "发单人id")
                             @PathVariable(value = "userId") Long userId,
                             @ApiParam(name = "orderId", value = "订单Id")
                             @PathVariable(value = "orderId") Long orderId,
                             HttpServletResponse response) {
        if(orderService.confirmOrderRequest(orderId))
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        else WebUtil.printApi(response, new Result(true).msg("确认成功！等待用户付款"));
    }
    
    /*工程师抢单*/
    @RequestMapping("request/{orderId}/order")
    @ApiOperation(notes = "工程师抢单", httpMethod = "POST", value = "抢单")
    public void requestOrder(@ApiParam(name = "orderId", value = "订单Id")
                             @PathVariable(value = "orderId") Long orderId,
                             HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.getById(orderId);
        if(order == null) {
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        RequestDate requestDate = requestDateService.requestOrder_IdAndUser_Id(orderId, userSessionModul.getId());
        if(requestDate != null) {
            WebUtil.printApi(response, new Result(false).msg("请勿重复抢单").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        User repair = userService.getById(userSessionModul.getId());
        RequestDate requestDateSave = new RequestDate(order, userService.getById(userSessionModul.getId()), false, new Date());
        requestDateService.create(requestDateSave);
        SendPush.pushToUser(order.getUser().getUserLoginInfo().getDeviceId(), "手机号为:" + repair.getLoginName().substring(0, 2) + "****" + repair.getLoginName().substring(7, 10)
                + "的用户向您发送了一个接单请求", "订单通知", "5", repair.getId().toString());
        WebUtil.printApi(response, new Result(true).msg("请求已发送，请等待对方确认"));
    }
    
    /*请求列表*/
    @RequestMapping("message/list/order")
    @ApiOperation(notes = "请求列表", httpMethod = "POST", value = "请求列表")
    public void messageList(@ApiParam(name = "orderId", value = "订单Id")
                            @RequestParam(value = "orderId") Long orderId,
                            @ApiParam(name = "pageNum", value = "页码")
                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                            HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        /*工程师*/
        WebUtil.printApi(response, new Result(true).data(requestDateService.findByTypeAndUserId(userSessionModul.getId(),
                orderId, userSessionModul.getType(), new PageRequest(pageNum-1,10))));
    }
    
    
    /*用户同意抢单请求*/
    @RequestMapping("confirm/{repairId}/and/{orderId}")
    @ApiOperation(notes = "用户同意抢单请求", httpMethod = "POST", value = "同意抢单")
    public void confirmRepair(@ApiParam(name = "repairId", value = "接单人Id")
                              @PathVariable(value = "repairId") Long repairId,
                              @ApiParam(name = "orderId", value = "订单Id")
                              @PathVariable(value = "orderId") Long orderId,
                              HttpServletResponse response) {
        if(orderService.confirmRepair(orderId, repairId)) {
            WebUtil.printApi(response, new Result(true).data(ApiBeanUtils.getOrderdetailModel(orderService.getById(orderId), userService.getById(repairId))));
        }
        WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
    }
    
    /*推送匹配订单给工程师拒绝*/
    @RequestMapping("denied/{userId}/to/{orderId}")
    @ApiOperation(notes = "拒绝订单", httpMethod = "POST", value = "拒绝订单(工)")
    public void deniedOrder(@ApiParam(name = "userId", value = "用户ID")
                            @PathVariable(value = "userId") Long userId,
                            @ApiParam(name = "orderId", value = "订单Id")
                            @PathVariable(value = "orderId") Long orderId,
                            HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.findByIdAndUser_Id(orderId, userId);
        UserPosition userPosition = userPositionService.findByTimeAndUserId(userId);
        if(order == null || userPosition == null) {
            WebUtil.printApi(response, new Result(false).msg("数据异常").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        AreasSearch areasSearch = new AreasSearch("43000", true, orderId, userPosition.getLng(), userPosition.getLat());
        areasSearchService.create(areasSearch);
        SendPush.pushToUser(order.getUser().getUserLoginInfo().getDeviceId(), "昵称为:" + userSessionModul.getNickName()
                + "的用户拒绝了您的订单请求", "消息通知", "5", order.getId().toString());
        WebUtil.printApi(response, new Result(true));
    }
    
    
    /*我的订单(工程师)*/
    @RequestMapping("mine/order/repair")
    @ApiOperation(notes = "我的订单：工-status 2=待支付，3=待上门,4=待评价，5=已完成，6=已取消" +
            "工-status 1 =已下单，2=已接单，3=已支付，4=待评价，5=已完成，6=已取消", httpMethod = "GET", value = "我的订单")
    public void mineOrderPage(@ApiParam(name = "status", value = "状态")
                              @RequestParam(value = "status", defaultValue = "0") Integer status,
                              @ApiParam(name = "pageNum", value = "页码")
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                              HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Map<String, Object> orderPage = orderService.mineOrderPage(userSessionModul.getId(), userSessionModul.getType(), status, new PageRequest(pageNum - 1, 10, Sort.Direction.DESC, "id"));
        WebUtil.printApi(response, new Result(true).data(orderPage));
    }
    
    /*查看我的订单详情*/
    @RequestMapping("details/{id}/order")
    @ApiOperation(notes = "查看订单详情，如果是用户只能查看自己发布的订单," +
            "如果是工程师只能查看自己所接的单子", httpMethod = "GET", value = "订单详情")
    public void orderDetails(@ApiParam(name = "id", required = true, value = "订单ID")
                             @PathVariable(value = "id") Long id, HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = userSessionModul.getType().equals("0") ? orderService.findByIdAndUser_Id(id, userSessionModul.getId()) :
                orderService.getById(id);
        if(order == null || order.isCut()) {
            WebUtil.printApi(response, new Result(false).msg("该订单不存在"));
            return;
        }
        if(userSessionModul.getType().equals("1")) {
            if(order.getStatus() != 1 && order.getRepair() != null && ! order.getRepair().getId().equals(userSessionModul.getId())) {
                WebUtil.printApi(response, new Result(false).msg("该订单不存在"));
                return;
            }
        }
        OrderModule orderModule = ApiBeanUtils.getOrderdetailModel(order, userSessionModul.getType().equals("0") ? order.getRepair() : order.getUser());
        TradeInfo tradeInfo = tradeInfoService.findByOrder_IdOrderByCreateTimeDesc(order.getId());
        if(tradeInfo != null) {
            orderModule.setPayTime(tradeInfo.getCreateTime());
        }
        Comments comments = commentsService.findByOrder_Id(order.getId());
        orderModule.setFinishTime(comments != null ? comments.getCreateTime() : null);
        Map<String,Object> orderMap=JsonUtil.json2Obj(JsonUtil.obj2Json(orderModule), Map.class);
        AreasSearch areasSearch=(AreasSearch)areasSearchService.findByOrderlngAndLat(orderModule.getId(),userSessionModul.getId()).getData();
        orderMap.put("lat",areasSearch.getLat());
        orderMap.put("lng",areasSearch.getLng());
        WebUtil.printApi(response, new Result(true).data(orderMap));
    }
    
    /*超过30分钟没有付款的订单自动取消*/
    public void orderOvertime() {
        orderService.updateExpiredOrderState();
    }
    
    /*取消订单*/
    @RequestMapping("cancel/{id}/order")
    @ApiOperation(notes = "用户:取消订单", httpMethod = "POST", value = "取消订单(用)")
    public void cancelOrder(@PathVariable(value = "id") Long id,
                            @RequestParam(value = "cancelRemark") String cancelRemark,
                            HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.findByIdAndUser_Id(id, userSessionModul.getId());
        if(order == null) {
            WebUtil.printApi(response, new Result(false).msg("订单不存在").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        order = orderService.cancel(order, cancelRemark);
        OrderModule orderModule = ApiBeanUtils.getOrderdetailModel(order, userSessionModul.getType().equals("0") ? order.getRepair() : order.getUser());
        WebUtil.printApi(response, new Result(true).msg("取消成功！").data(orderModule));
    }
    
    //删除订单(逻辑删除)
    @RequestMapping("delete/{orderId}/order")
    @ApiOperation(notes = "删除订单", httpMethod = "POST", value = "删除订单")
    public void deleteOrder(@PathVariable(value = "orderId") Long orderId,
                            HttpServletResponse response) {
        UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
        Order order = orderService.findByIdAndUser_IdOrRepairId(orderId, userSessionModul.getId());
        if(order == null || order.getStatus() < 5) {
            WebUtil.printApi(response, new Result(false).msg("非法操作").data(EASY_ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        orderService.cutOrder(order);
        WebUtil.printApi(response, new Result(true).msg("删除成功!"));
    }
    
    //获取订单发单时的经纬度
    @RequestMapping(value = "positioning/{repairId}/order",method = RequestMethod.GET)
    @ApiOperation(notes = "用户获取工程师最新的经纬度", httpMethod = "GET", value = "订单发单经纬度")
    public void positioningUser(@PathVariable(value = "repairId") Long repairId, HttpServletResponse response) {
        try {
            UserSessionModul userSessionModul = (UserSessionModul) SessionUtils.getCurrentUser();
            WebUtil.printJson(response,JsonUtil.obj2ApiJson(userPositionService.findByRepairLngAndLat(repairId,userSessionModul.getId()),"user"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.printJson(response,new Result(false).msg("系统错误").data(EASY_ERROR_CODE.ERROR_CODE_0002));
        }
    }
    
}
