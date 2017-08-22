package com.dh.web.api.appApiController;

import com.dh.commont.*;
import com.dh.commont.bean.Result;
import com.dh.commont.jpush.SendPush;
import com.dh.configure.Consts;
import com.dh.entity.RepairOrder;
import com.dh.entity.RepairOrderLog;
import com.dh.entity.User;
import com.dh.entity.WordBook;
import com.dh.service.OrderCostTypeService;
import com.dh.service.OrderService;
import com.dh.service.UserService;
import com.dh.service.WordBookService;
import com.dh.web.api.BaseApiController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Coolkid on 2016/9/13.
 */
@RestController
@RequestMapping("api/order")
public class OrderApiController extends BaseApiController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WordBookService wordBookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderCostTypeService orderCostTypeService;
    /**
     * 定义图片保存目录
     */
    private static String folder = "order/";
    /**
     * 定义图片压缩后的宽度 0则按高等比压缩
     */
    private static String targetWidth = "330";
    /**
     * 定义图片压缩后的高度 0则按宽等比压缩
     */
    private static String targetHeight = "0";
    private String orderImgs = "";
    private String orderImgsThumb = "";

    /**
     * 用户 获取我的订单列表
     * 获取工程师订单记录
     * 根据关键字查询订单
     * 上个方法全为分页
     *
     * @param request
     * @param pageNumber
     * @param orderState
     * @return
     */
    @RequestMapping(value = "orderList")
    public void oderList(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNumber,
                         @RequestParam(value = "orderState", defaultValue = "") String orderState,
                         @RequestParam(value = "repairId", defaultValue = "") Integer repairId,
                         @RequestParam(value = "repairLike", defaultValue = "") String repairLike
    ) {
        //验证参数
        boolean paramBloon = false;
        if (StringUtils.isEmpty(orderState) && StringUtils.isEmpty(repairId) && !StringUtils.isEmpty(repairLike)) {
            paramBloon = true;
        }
        if (!StringUtils.isEmpty(orderState) && StringUtils.isEmpty(repairId) && StringUtils.isEmpty(repairLike)) {
            paramBloon = true;
        }
        if (StringUtils.isEmpty(orderState) && !StringUtils.isEmpty(repairId) && StringUtils.isEmpty(repairLike)) {
            paramBloon = true;
        }
        /*只有pageNum不为空的情况，查询全部订单状态*/
        if (StringUtils.isEmpty(orderState) && StringUtils.isEmpty(repairId) && StringUtils.isEmpty(repairLike)) {
            paramBloon = true;
        }
        if(pageNumber==null){
            paramBloon = false;
        }
        if (StringUtils.isEmpty(pageNumber) || !paramBloon) {
            WebUtil.printApi(response, new Result(false).msg("参数错误"));
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        // 根据不同的角色获取订单列表
        Page<RepairOrder> page = orderService.orderPage(pageNumber, PAGE_SIZE, orderState, user,repairId, repairLike);
        for (RepairOrder repairOrder : page.getContent()) {
            String reType = wordBookService.findByIndex(Integer.parseInt(repairOrder.getRepairType()), REPAIR_TYPE);
            repairOrder.setRepairType(reType);
            if (repairOrder.getRepair() != null) {
                repairOrder.setRepairName(repairOrder.getRepair().getUserName());
            }
        }
        List<WordBook> wordBooks = wordBookService.findByType(Consts.ORDER_STATE);
        List<RepairOrder> repairOrderList = new ArrayList<RepairOrder>();
        for (WordBook wordBook : wordBooks) {
            for (RepairOrder repairOrder : page.getContent()) {
                if (wordBook.getWordIndex().toString().equals(repairOrder.getOrderState())) {
                    repairOrder.setSort(wordBook.getOrderBy());
                    repairOrderList.add(repairOrder);
                }
            }
        }
        Map<String, Object> dataMap = APIFactory.fitting(page);
        dataMap.put("o", repairOrderList);
        String result = JsonUtil.obj2ApiJson(new Result(true).msg("获取成功").data(repairOrderList).otherData(dataMap.get("e")), "repair", "user", "appointmentTime",
                "completionTime", "contactPhone", "contactAddress", "contactLocation", "orderImgs", "orderImgsThumb",
                "updateTime", "commentContent", "commentStar", "hasAgain", "hasComment", "orderSource", "docPath", "docName", "ccIds",
                "orderExplain", "qty");
        WebUtil.printApi(response, result);
    }

    /**
     * 客户发布订单
     *
     * @param request
     * @param repairType      订单类型
     * @param contactUser     联系人
     * @param contactPhone    联系电话
     * @param contactAddress  联系地址
     * @param orderDesc       订单描述
     * @param imgStrs         图片base64字符串
     * @param contactLocation 联系人地址经纬度
     * @return
     */
    @RequestMapping(value = "saveOrder")
    public Map<String, Object> saveOrder(HttpServletRequest request,
                                         @RequestParam(value = "repairType", defaultValue = "0") Integer repairType,
                                         @RequestParam(value = "contactUser", defaultValue = "") String contactUser,
                                         @RequestParam(value = "contactPhone", defaultValue = "") String contactPhone,
                                         @RequestParam(value = "contactAddress", defaultValue = "") String contactAddress,
                                         @RequestParam(value = "orderDesc", defaultValue = "") String orderDesc,
                                         @RequestParam(value = "imgStrs", defaultValue = "") String imgStrs,
                                         @RequestParam(value = "orderCost", defaultValue = "") String orderCost,
                                         @RequestParam(value = "contactLocation", defaultValue = "") String contactLocation,
                                         @RequestParam(value = "qty", defaultValue = "0") Integer qty) {
        if (CommonButil.isEmpty(contactUser) || CommonButil.isEmpty(contactPhone) || CommonButil.isEmpty(contactAddress)
                || CommonButil.isEmpty(orderDesc) || CommonButil.isEmpty(contactLocation) || CommonButil.isEmpty(qty)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        if (1 == repairType && 5 != user.getRole().getId()) {
            return setReturnValue("仅内部使用", RETURN_CODE_FAILED, null, null, "4009");
        }
        orderImgs = "";
        orderImgsThumb = "";
        if (!CommonButil.isEmpty(imgStrs)) {
            String userFolder = folder + user.getId() + "/";
            Map<String, Object> map = uploadImg(imgStrs, userFolder);
            if (!CommonButil.isEmpty(map)) {
                return map;
            }
        }
        // 保存订单
        RepairOrder order = orderService.saveOrder(orderCost, repairType, contactUser, contactPhone, contactAddress, orderDesc,
                orderImgs, orderImgsThumb, contactLocation, user.getId(), qty);
        if (null == order) {
            return setReturnValue("发布失败", RETURN_CODE_FAILED);
        }

        // 获取所有工程师和经理的设备id推送
        List<String> repairDeviceIds = orderService.getAllRepairDeviceId();
        if (!CommonButil.isEmpty(repairDeviceIds)) {
            String repairTypeStr = wordBookService.findByIndex(repairType, REPAIR_TYPE);
            String content = "用户 " + user.getUserName() + " 发布了一个 " + repairTypeStr + " 的订单，请注意查看！";
            SendPush.pushToUser(repairDeviceIds, content, "", "1", order.getId().toString());
        }
        return setReturnValue("订单:" + order.getOrderNum() + " 发布成功，48小时内会有工程师受理，请耐心等待。", RETURN_CODE_SUCC, order.getId());
    }


    /**
     * 请求上传图片
     *
     * @param imgStrs
     * @param userFolder
     * @return
     */
    private Map<String, Object> uploadImg(String imgStrs, String userFolder) {
        // 设置上传头像参数
        Map<String, String> paramMap = new HashMap<String, String>();
        try {
            // url转码
            paramMap.put("imgStrs", URLEncoder.encode(imgStrs, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return setReturnValue("参数转码发生错误", RETURN_CODE_FAILED, null, null, "4000");
        }
        // 添加文件路径、设置图片压缩宽度、高度
        paramMap.put("folder", userFolder);
        paramMap.put("targetWidth", targetWidth);
        paramMap.put("targetHeight", targetHeight);

        // 请求url
        String requestUrl = APP_BASE_URL + "/api/uploadImgsThumb";
        String resultJson = HttpRequestButil.sendPost(requestUrl, paramMap);
        if (CommonButil.isEmpty(resultJson)) {
            return setReturnValue("上传图片失败", RETURN_CODE_FAILED);
        }

        JSONObject result = new JSONObject(resultJson);
        // 获取resultJson 为实体对象
        if (result.getInt("c") == 0) {
            return setReturnValue(result.getString("m"), RETURN_CODE_FAILED);
        }
        // 获取 图片路径
        JSONArray imgUrlArr = result.getJSONArray("o");
        if (CommonButil.isEmpty(imgUrlArr)) {
            return setReturnValue("保存图片失败", RETURN_CODE_FAILED);
        }
        orderImgs = "";
        orderImgsThumb = "";
        for (int i = 0; i < imgUrlArr.length(); i++) {
            String imgUrl = imgUrlArr.getString(i);
            String[] arr = imgUrl.split(",");
            if (arr.length == 2) {
                orderImgs += (("".equals(orderImgs) ? "" : ",") + arr[0]);
                orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + arr[1]);
            } else {
                orderImgs += (("".equals(orderImgs) ? "" : ",") + null);
                orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + null);
            }
        }
        return null;
    }

    /**
     * 获取订单详情
     *
     * @param request
     * @param orderId
     * @return
     */
    @RequestMapping(value = "orderDetail")
    public void orderDetail(HttpServletRequest request,HttpServletResponse response,
                                           @RequestParam(value = "orderId", defaultValue = "") Integer orderId) {
        if (CommonButil.isEmpty(orderId)) {
            WebUtil.printApi(response, new Result(false).msg("请求参数错误").error("4004"));
            return;
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg("用户登录超时，请重新登录").error("4001"));
            return;
        }
        RepairOrder order=orderService.getById(orderId);
        if (order.getRepair() != null&&order.getRepair().getId()==user.getId()&&order.getIsRead()==0){
            if (orderService.updateIsRead(order.getId())>0){
                order.setIsRead(1);
            }
        }
        order = orderService.getShowOrderDetail(orderId, user.getId());
        if (null == order) {
            WebUtil.printApi(response, new Result(false).msg("获取失败，该订单不存在"));
            return;
        }
        String result = JsonUtil.obj2ApiJson(new Result(true).msg("获取成功").data(order),"salt","deviceId",
                "updateTime", "commentContent", "commentStar", "hasAgain", "hasComment", "orderSource",
                "ccIds","repairOrder","password");
        WebUtil.printApi(response, result);
    }

    /**
     * 获取订单全部状态
     *
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping(value = "orderStateDetail")
    public void orderStateDetail(HttpServletResponse response,
                                 @RequestParam(value = "orderId", defaultValue = "") Integer orderId) {
        if (CommonButil.isEmpty(orderId)) {
            WebUtil.printApi(response,new Result(false).msg("请求参数错误").error("4004"));
        }
        String result=JsonUtil.obj2ApiJson(new Result(true).msg("获取成功").data(orderService.getOrderStateByOrderId(orderId)),"orderNum"
            ,"repairType","orderState","user","repair","appointmentTime","completionTime"
            ,"contactUser","contactPhone","contactAddress","contactLocation","orderDesc","orderImgs"
            ,"orderImgsThumb","updateTime","commentContent","hasAgain","hasComplain"
            ,"hasComment","isRead","orderSource","ccIds","orderExplain","qty","uploadFileOrdersList"
            ,"repairName","orderCostList","orderEngineerList","sort","password","salt","registerDate"
            ,"userIco","userSex","userAddress","userCompany","userMail","lastLocation","lastLocationTime"
            ,"loginCount","deviceOs","deviceId","isLogin","userState","userType","role","group","isLocked","currentOrderCount"
            ,"totalOrderCount","plainPassword","typeStr");
        WebUtil.printApi(response,result);
    }

    /**
     * 工程师修改订单预约时间
     *
     * @param request
     * @param orderId
     * @param appointmentTime
     * @return
     */
    @RequestMapping(value = "updateAppointmentTime")
    public Map<String, Object> updateAppointmentTime(HttpServletRequest request,
                                                     @RequestParam(value = "orderId", defaultValue = "") Integer orderId,
                                                     @RequestParam(value = "appointmentTime", defaultValue = "") Long appointmentTime) {

        if (CommonButil.isEmpty(orderId) || CommonButil.isEmpty(appointmentTime)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.getById(orderId);
        if (null == order) {
            return setReturnValue("该订单不存在，请重新再试", RETURN_CODE_FAILED);
        }
        // 判断订单的接单人 是否是当前操作人
        if (order.getRepair() == null || user.getId() != order.getRepair().getId()) {
            return setReturnValue("操作错误，您不是当前订单的接单人，无法修改", RETURN_CODE_FAILED);
        }

        Date appointmentTimeDate = new Date(appointmentTime);
        String appointmentTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(appointmentTimeDate);

        // 修改订单预约时间
        order.setAppointmentTime(appointmentTimeDate);
        orderService.updateAppointmentTime(order, appointmentTimeStr, user.getId());

        // 获取发单人设备id推送
        String userDeviceId = userService.findUserDeviceId(order.getUser().getId());
        if (!CommonButil.isEmpty(userDeviceId)) {
            String content = "工程师 " + user.getUserName() + " 修改了订单：" + order.getOrderNum() + " 的预约时间，请注意查看。";
            SendPush.pushToUser(userDeviceId, content, "", "2", order.getId().toString());
        }
        return setReturnValue("订单:" + order.getOrderNum() + " 修改预约时间为" + appointmentTimeStr, RETURN_CODE_SUCC, appointmentTimeStr);
    }

    /**
     * 工程师列表
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "repairList")
    public void repairList(HttpServletResponse response) {
        String result = JsonUtil.obj2ApiJson(new Result(true).msg("获取成功").data(orderService.findAllRepairList(true,null)),
                "isLocked","group","password","salt","deviceId","deviceOs");
        WebUtil.printApi(response,result);
    }

    /**
     * 工程师相关人列表
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "repairCCList")
    public void repairCCList(HttpServletResponse response,
                                            @RequestParam(value = "orderId", defaultValue = "") Integer orderId) {
        RepairOrder repairOrder = orderService.getById(orderId);
        if (repairOrder.getRepair() == null) {
            WebUtil.printApi(response,new Result(false).msg("该订单还没有工程师接单"));
        }
        WebUtil.printApi(response,JsonUtil.obj2ApiJson(new Result(true).msg("获取成功").data(orderService.findAllRepairList(false,repairOrder)),
                "isLocked","group","password","salt","deviceId","deviceOs"));
    }


    /**
     * 修改订单状态 接单、完成订单
     *
     * @param request
     * @param orderId 订单id
     * @param type    类型 1 接单 2完成订单
     * @return
     */
    @RequestMapping(value = "updateOrderState")
    @ResponseBody
    public Map<String, Object> updateOrderState(HttpServletRequest request,
                                                @RequestParam(value = "orderId", defaultValue = "") Integer orderId,
                                                @RequestParam(value = "type", defaultValue = "") Integer type,
                                                @RequestParam(value = "orderExplain", defaultValue = "") String orderExplain) {
        if (CommonButil.isEmpty(orderId) || CommonButil.isEmpty(type)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User repair = (User) request.getSession().getAttribute("currentUser");
        if (null == repair) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.getById(orderId);
        if (null == order) {
            return setReturnValue("该订单不存在，请重新再试", RETURN_CODE_FAILED);
        }
        // 获取发单人设备id推送
        String userDeviceId = userService.findUserDeviceId(order.getUser().getId());
        String message = "";
        if (1 == type) {// 抢单
            message = "订单:" + order.getOrderNum() + " 已接单";
            if (!CommonButil.isEmpty(order.getRepair())) {
                return setReturnValue("该订单已被受理，请选择其他订单", RETURN_CODE_FAILED);
            }
            // 修改订单状态为：已接单
            orderService.updateOrderState(1, order, repair, orderExplain);
            // 推送给用户
            if (!CommonButil.isEmpty(userDeviceId)) {
                String content = "您的订单：" + order.getOrderNum() + " 已被受理，接单人：" + repair.getUserName() + ",请注意查看";
                SendPush.pushToUser(userDeviceId, content, "", "3", order.getId().toString());
            }
        } else if (2 == type) {// 完成订单
            message = "订单:" + order.getOrderNum() + " 已完成";
            if (order.getOrderState().equals("2") || CommonButil.isEmpty(order.getRepair().getId())
                    || order.getRepair().getId() != repair.getId()) {
                return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
            }
            // 修改订单状态为：已完成
            orderService.updateOrderState(2, order, repair, orderExplain);
            // 推送给用户
            if (!CommonButil.isEmpty(userDeviceId)) {
                String content = "您的订单：" + order.getOrderNum() + " 已完成，请注意查看";
                SendPush.pushToUser(userDeviceId, content, "", "4", order.getId().toString());
            }
        } else if (5 == type) { //订单支付
            message = "订单:" + order.getOrderNum() + " 已支付";
            if ((!order.getOrderState().equals("2"))|| CommonButil.isEmpty(order.getRepair())
                    || order.getUser().getId() != repair.getId()) {
                return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
            }
            if (order.getUser().getRole().getId()!=5){
                return setReturnValue("支付方式选择有误，请重新选择", RETURN_CODE_FAILED);
            }
            // 修改订单状态为：已支付
            orderService.updateOrderState(5, order, repair, orderExplain);
            // 获取工程师设备id推送
            String repairDeviceId = repair.getDeviceId();
            if (!CommonButil.isEmpty(repairDeviceId)) {
                String content = "您的订单：" + order.getOrderNum() + "" + " 已支付,请注意查看";
                SendPush.pushToUser(repairDeviceId, content, "", "6", order.getId().toString());
            }
        } else {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        return setReturnValue(message, RETURN_CODE_SUCC, order.getId());
    }

    /**
     * 给订单分配相关人
     *
     * @param request
     * @param orderId   订单id
     * @param repairIds 工程师id
     * @return
     */
    @RequestMapping(value = "assignRepairCC")
    @ResponseBody
    public Map<String, Object> assignRepairCC(HttpServletRequest request,
                                              @RequestParam(value = "orderId", defaultValue = "") Integer orderId,
                                              @RequestParam(value = "repairIds", defaultValue = "") String repairIds) {
        if (CommonButil.isEmpty(orderId) || CommonButil.isEmpty(repairIds)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 判断是否是经理
        if (2 != user.getUserType()) {
            return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.getById(orderId);
        if (null == order) {
            return setReturnValue("该订单不存在，请重新再试", RETURN_CODE_FAILED);
        }
        // 只有在待处理和已接单情况下 才能分配订单
        if (!order.getOrderState().equals("0") && !order.getOrderState().equals("1")) {
            return setReturnValue("分配失败，该订单已完成/已取消", RETURN_CODE_FAILED);
        }

        //只有订单已经有接单人才可分配相关人
        if (CommonButil.isEmpty(order.getRepair().getId())) {
            return setReturnValue("请先分配该订单的接单人后再次尝试此操作", RETURN_CODE_FAILED);
        }

        RepairOrder savedOrder = orderService.assignRepairCC(order, repairIds, user.getId());

        // 获取工程师设备id推送
        String repairDeviceId = userService.findUserDeviceId(savedOrder.getRepair().getId());
        if (!CommonButil.isEmpty(repairDeviceId)) {
            String content = "订单：" + savedOrder.getOrderNum() + "，已更新相关人" + " ，请注意查看";
            SendPush.pushToUser(repairDeviceId, content, "", "3", order.getId().toString());
        }

        return setReturnValue("订单:" + savedOrder.getOrderNum() + " 已更新相关人:" + savedOrder.getRepairName(), RETURN_CODE_SUCC, savedOrder.getId());
    }

    /**
     * 给订单分配工程师
     *
     * @param request
     * @param orderId  订单id
     * @param repairId 工程师id
     * @return
     */
    @RequestMapping(value = "assignRepair")
    @ResponseBody
    public Map<String, Object> assignRepair(HttpServletRequest request,
                                            @RequestParam(value = "orderId", defaultValue = "") Integer orderId,
                                            @RequestParam(value = "repairId", defaultValue = "") Integer repairId) {
        if (CommonButil.isEmpty(orderId) || CommonButil.isEmpty(repairId)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
       // 判断是否是经理
        if (2 != user.getUserType()) {
            return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED, null, null, "4001");
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.getById(orderId);
        if (null == order) {
            return setReturnValue("该订单不存在，请重新再试", RETURN_CODE_FAILED);
        }

        // 只有在待处理和已接单情况下 才能分配订单
        if (order.getOrderState().equals("0") && order.getOrderState().equals("1")) {
            return setReturnValue("分配失败，该订单已完成/已取消", RETURN_CODE_FAILED);
        }

        // 获取要分配的工程师
        User repair = userService.getById(repairId);
        if (null == repair) {
            return setReturnValue("该工程师不存在，请重新再试", RETURN_CODE_FAILED);
        }
      /*  // 判断repairId 是否是工程师
        if (1 != repair.getUserType() && 2 != repair.getUserType()) {
            return setReturnValue("非工程师不能接单，请重新再试", RETURN_CODE_FAILED);
        }*/

        // 修改订单状态为：已接单
        RepairOrder savedOrder = orderService.assignRepair(order, repair, user.getId());

        // 获取发单人设备id推送
        String userDeviceId = userService.findUserDeviceId(order.getUser().getId());
        if (!CommonButil.isEmpty(userDeviceId)) {
            String content = "您的订单：" + order.getOrderNum() + " 已被受理，接单人：" + user.getUserName() + ",请注意查看";
            SendPush.pushToUser(userDeviceId, content, "", "3", order.getId().toString());
        }

        // 获取工程师设备id推送
        String repairDeviceId = repair.getDeviceId();
        if (!CommonButil.isEmpty(repairDeviceId)) {
            String content = "您已被分配订单：" + order.getOrderNum() + "" + " ,请注意查看";
            SendPush.pushToUser(repairDeviceId, content, "", "3", order.getId().toString());
        }

        return setReturnValue("订单:" + savedOrder.getOrderNum() + " 已分配给:" + repair.getUserName(), RETURN_CODE_SUCC, savedOrder.getId());
    }

    /**
     * 获取工程师详情
     *
     * @param response
     * @param repairId
     * @return
     */
    @RequestMapping(value = "repairDetail")
    public void repairDetail(HttpServletResponse response,
                                            @RequestParam(value = "repairId", defaultValue = "") Integer repairId) {
        if (CommonButil.isEmpty(repairId)) {
            WebUtil.printApi(response,new Result(false).msg("请求参数错误").error("4004"));
            return;
        }
        User user = orderService.findRepairDetail(repairId);
        if (null == user) {
            WebUtil.printApi(response,new Result(false).msg("该用户不存在，请重新再试"));
        }
        WebUtil.printApi(response,new Result(true).msg("获取成功").data(user));
    }

    /**
     * 获取提交内容类型 如：投诉内容类型、取消订单、再次上门的事由
     *
     * @param request
     * @param type    1取消订单 2投诉 3再次上门
     * @return
     */
    @RequestMapping(value = "getContentType")
    @ResponseBody
    public Map<String, Object> getContentType(HttpServletRequest request,
                                              @RequestParam(value = "type", defaultValue = "") Integer type) {
        if (CommonButil.isEmpty(type)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        return setReturnValue("获取成功", RETURN_CODE_SUCC, wordBookService.getContentType(type));
    }

    /**
     * 保存事由 取消订单、投诉、再次上门
     *
     * @param request
     * @param type    1取消订单、2投诉、3再次上门
     * @return
     */
    @RequestMapping(value = "saveReason")
    @ResponseBody
    public Map<String, Object> saveReason(HttpServletRequest request,
                                          @RequestParam(value = "type", defaultValue = "") Integer type,
                                          @RequestParam(value = "contentType", defaultValue = "") Integer contentType,
                                          @RequestParam(value = "msgContent", defaultValue = "") String msgContent,
                                          @RequestParam(value = "orderId", defaultValue = "") Integer orderId) {
        if (CommonButil.isEmpty(type) || CommonButil.isEmpty(contentType) || CommonButil.isEmpty(orderId)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }
        RepairOrder order = orderService.getById(orderId);
        if (null == order) {
            return setReturnValue("该订单不存在，请重新再试", RETURN_CODE_FAILED);
        }
        // 只有订单为待处理状态下 用户才可以取消订单 并且当前用户是发单人
        if (type == 1 && (!order.getOrderState().equals("0") || user.getId() != order.getUser().getId())) {
            return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
        } else
            // 只有订单在 待处理 和已接单的状态下 工程师才可以投诉 并且当前用户是发单人
            if (type == 2
                    && (order.getOrderState().equals(3) || order.getOrderState().equals(4) || user.getId() != order.getUser().getId())) {
                return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
            } else
                // 只有订单在已接单的状态下 工程师才可以再次上门 并且当前用户是接单人
                if (type == 3 && (!order.getOrderState().equals("1") || user.getId() != order.getRepair().getId())) {
                    return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
                }

        RepairOrderLog savedRol = orderService.saveReason(type, contentType, msgContent, order, user);
        if (null == savedRol) {
            return setReturnValue("提交失败，请重新再试", RETURN_CODE_FAILED);
        }
        String message = "";
        switch (type) {
            case 1:
                message = "订单:" + order.getOrderNum() + " 已取消";
                break;
            case 2:
                message = "订单:" + order.getOrderNum() + " 已投诉";
                // 获取接单人设备id推送
                List<String> repairDeviceId = orderService.findManagerDeviceId();
                if (!CommonButil.isEmpty(repairDeviceId)) {
                    String content = "订单:" + order.getOrderNum() + " 有用户投诉，请及时处理";
                    SendPush.pushToUser(repairDeviceId, content, "", "3", order.getId().toString());
                }
                break;
            case 3:
                message = "订单:" + order.getOrderNum() + " 未完成，需再次上门服务";
                break;
            default:
                break;
        }
        return setReturnValue(message, RETURN_CODE_SUCC);
    }


    /**
     * 保存评论
     *
     * @param request
     * @param commentStar    评星
     * @param commentContent 评论
     * @param orderId
     * @return
     */
    @RequestMapping(value = "saveComment")
    @ResponseBody
    public Map<String, Object> saveComment(HttpServletRequest request,
                                           @RequestParam(value = "commentStar", defaultValue = "") String commentStar,
                                           @RequestParam(value = "commentContent", defaultValue = "") String commentContent,
                                           @RequestParam(value = "orderId", defaultValue = "") Integer orderId) {
        if (CommonButil.isEmpty(commentStar) || CommonButil.isEmpty(orderId)) {
            return setReturnValue("请求参数错误", RETURN_CODE_FAILED, null, null, "4004");
        }
        // 获取当前用户
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            return setReturnValue("用户登录超时，请重新登录", RETURN_CODE_FAILED, null, null, "4001");
        }

        RepairOrder order = orderService.getById(orderId);
        if (null == order) {
            return setReturnValue("该订单不存在，请重新再试", RETURN_CODE_FAILED);
        }
        if (order.getRepairType().equals("0")) {
            // 只有订单完成的时候才能评价 并且是自己的订单
            if (!order.getOrderState().equals("2") || user.getId() != order.getUser().getId()) {
                return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
            }
        } else {
            // 只有订单完成的时候才能评价 并且是自己的订单
            if (!order.getOrderState().equals("5") || user.getId() != order.getUser().getId()) {
                return setReturnValue("非法操作，请重新再试", RETURN_CODE_FAILED);
            }
        }
        // 保存评价
        RepairOrderLog rol = orderService.saveComment(order, commentStar, commentContent, user.getId());
        if (null == rol) {
            return setReturnValue("提交失败，请稍后再试", RETURN_CODE_FAILED);
        }
        String repairDeviceId = userService.findUserDeviceId(order.getRepair().getId());
        if (!CommonButil.isEmpty(repairDeviceId)) {
            String content = "您处理的订单" + order.getOrderNum() + " 有用户评价了";
            SendPush.pushToUser(repairDeviceId, content, "", "3", order.getId().toString());
        }
        return setReturnValue("订单:" + order.getOrderNum() + " 已评价", RETURN_CODE_SUCC);
    }

    /**
     * 获取订单费用类型
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "getOrderCostType")
    @ResponseBody
    public Map<String, Object> getOrderCostType(HttpServletRequest request) {
        return setReturnValue("查询成功", RETURN_CODE_SUCC, orderCostTypeService.getOrderCostType());
    }

}
