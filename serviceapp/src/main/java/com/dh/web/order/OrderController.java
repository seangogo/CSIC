package com.dh.web.order;

import com.dh.common.CommonButil;
import com.dh.common.UploadFileUtils;
import com.dh.common.UploadImgButil;
import com.dh.common.WebUtil;
import com.dh.common.bean.Result;
import com.dh.common.gson.JsonUtil;
import com.dh.common.jpush.SendPush;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.dto.ShowRepair;
import com.dh.entity.*;
import com.dh.service.account.AccountService;
import com.dh.service.account.ShiroDbRealm;
import com.dh.service.order.OrderCostService;
import com.dh.service.order.OrderEngineerService;
import com.dh.service.order.OrderService;
import com.dh.service.system.ResourceService;
import com.dh.service.system.WordBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 订单管理Controller
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private WordBookService wordService;
    @Autowired
    private AccountService userService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private OrderEngineerService orderEngineerService;
    @Autowired
    private OrderCostService orderCostService;
    /**
     * 定义图片压缩后的宽度 0则按高等比压缩
     */
    private static Integer targetWidth = 330;
    /**
     * 定义图片压缩后的高度 0则按宽等比压缩
     */
    private static Integer targetHeight = 0;

    /**
     * 保存图片路径
     */
    private static String folder = "order/";

    @RequiresPermissions("order:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @SystemControllerLog(description = "查看订单列表")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                       @RequestParam(value = "startDate", defaultValue = "") String startDate,
                       @RequestParam(value = "endDate", defaultValue = "") String endDate, Model model,
                       ServletRequest request) throws Exception {
    	String username =request.getParameter("search_LIKE_user.userName");
    	String repairname =request.getParameter("search_LIKE_repair.userName");
    	Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        if(username != null && username != ""){
        	String name=new String(username.getBytes("ISO-8859-1"),"UTF-8");
        	searchParams.put("LIKE_user.userName", name);
        }
        if(repairname!=null && repairname != ""){
        	String repair=new String(repairname.getBytes("ISO-8859-1"),"UTF-8");
        	searchParams.put("LIKE_repair.userName",repair);
        }
        
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            model.addAttribute("message", "登录超时，请重新登录");
            return "order/list";
        }
        Page<RepairOrder> orderList = orderService.getOrderList(user, startDate, endDate, searchParams, pageNumber, Consts.PAGE_SIZE);

        model.addAttribute("orderList", orderList);
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("repairTypeList", mapper.writeValueAsString(wordService.findByType(Consts.REPAIR_TYPE)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("currentUser", user);
        model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
        model.addAttribute("param_search_EQ_hasComplain",searchParams.get("EQ_hasComplain"));
        model.addAttribute("param_search_EQ_hasAgain",searchParams.get("EQ_hasAgain"));
        model.addAttribute("param_search_LIKE_user_userName",searchParams.get("LIKE_user.userName"));
        model.addAttribute("param_search_LIKE_repair_userName",searchParams.get("LIKE_repair.userName"));
        model.addAttribute("index", "order/list");
        model.addAttribute("tableType", resourceService.findTabeType("order/list"));
        return "order/list";
    }

    /**
     * 订单详情
     *
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("order:view")
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    @SystemControllerLog(description = "查看订单详情")
    public String detail(Model model, @PathVariable("id") Long id) {
        User user = userService.getUser(getCurrentUserId());
        if (CommonButil.isEmpty(user)) {
            model.addAttribute("message", "登录超时，请重新登录");
            return "order/detail";
        }
        RepairOrder order = orderService.getOrderById(id);
        /*获得订单附件*/
        model.addAttribute("fileList", orderService.findFilesByOrderId(id));
        model.addAttribute("currentUser", user);
        model.addAttribute("repairList", orderService.getRepairList());
        model.addAttribute("orderStates", orderService.findByOrderIdOrderByCreateTimeDesc(id));
        model.addAttribute("orderCost", orderService.findByOrderIdOrderByIdAsc(id));
        model.addAttribute("orderEngineer", orderService.getOrderEngineerByOId(id));
        if (!CommonButil.isEmpty(order.getRepairId()) && order.getRepairId() == user.getId()) {
            order.setIsRead(1);
            orderService.saveOrder(order);
        }
        model.addAttribute("ccRepair", orderService.getRepairCcName(order.getCcIds()));
        /*全部相关人*/
        List<ShowRepair> repairCCAllList = orderService.getOrderAllCCList();
        /*已经分配的相关人ID*/
        List<Long> repairToId = orderEngineerService.findEngineerByOrderIdNotRepair(order.getId());
        model.addAttribute("repairCCListAll", JsonUtil.obj2Json(repairCCAllList));
        /*未分配的相关人*/
        List<ShowRepair> repairsCClist = new ArrayList<ShowRepair>();
        List<ShowRepair> repairsCClistTo = new ArrayList<ShowRepair>();
        /*分配过相关人*/
        if (repairCCAllList != null && repairCCAllList.size() > 0) {
            if (repairToId != null && repairToId.size() > 0) {
                for (ShowRepair showRepair : repairCCAllList) {
                    if (repairToId.contains(showRepair.getRepairId())) {
                        repairsCClistTo.add(showRepair);
                    } else {
                        if (showRepair.getRepairId() != order.getRepairId()) {
                            repairsCClist.add(showRepair);
                        }
                    }
                }
            /*未分配的相关人*/
                model.addAttribute("repairCCList", repairsCClist);
            /*已分配相关人*/
                model.addAttribute("repairCCListTo", repairsCClistTo);
            } else {
                model.addAttribute("repairCCList", repairCCAllList);
            }
        }
        model.addAttribute("order", order);
        model.addAttribute("index", "order/list");
        model.addAttribute("tableType", resourceService.findTabeType("order/list"));
        return "order/detail";
    }

    /**
     * 工程师相关人列表
     *
     * @param orderId
     * @return
     */
    @RequiresPermissions("order:view")
    @RequestMapping(value = "repairCCList")
    @SystemControllerLog(description = "查看订单详情")
    @ResponseBody
    /*查询当权工程已分配的相关人和可以分配的所有相关人*/
    public Result repairCCList(HttpServletResponse response,@RequestParam(value = "orderId", defaultValue = "") Long orderId) {
        return new Result(true).data(orderService.findRepairCCList(orderId))
                .otherData(orderService.findIdsByRepair(orderId));
    }


    /**
     * 添加相关人员和工程师
     *
     * @param orderId
     * @param repairIds
     * @return
     */
    @RequiresPermissions("order:assign")
    @RequestMapping(value = "assignRepairs", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "分配订单")
    public String assignRepairs(@RequestParam(value = "orderId", defaultValue = "") Long orderId,
                                @RequestParam(value = "repairId", defaultValue = "") Long repairId,
                                @RequestParam(value = "repairIds", defaultValue = "") String repairIds) {
        if (CommonButil.isEmpty(orderId) || CommonButil.isEmpty(repairId)) {
            return "请求参数错误";
        }

        User user = userService.getUser(getCurrentUserId());
        if (CommonButil.isEmpty(user)) {
            return "登录超时，请重新登录";
        }
        // 判断是否是经理
        if (2 != user.getUserType() && 3 != user.getUserType()) {
            return "非法操作，请重新再试";
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.findOne(orderId);
        if (null == order) {
            return "该订单不存在，请重新再试";
        }

        // 只有在待处理和已接单情况下 才能分配相关人
        if (0 != order.getOrderState() && 1 != order.getOrderState()) {
            return "分配失败，该订单已完成/已取消";
        }
        orderEngineerService.deleteAllCCByOderId(orderId);
        // 获取要分配的工程师
        if(!CommonButil.isEmpty(repairIds)){
            String[] id = repairIds.split(",");
            String repairIdsName = "";
           /* List<Long> repairCClistTo=orderEngineerService.findEngineerByOrderIdNotRepair(order.getId());*/
            for (int i = 0; i < id.length; i++) {
                User repair = userService.getUser(Long.valueOf(id[i]));
               /* if(repairCClistTo.contains(Long.valueOf(id[i]))){
                    continue;
                }*/
                if (null != repair) {
                    repairIdsName += repair.getUserName() + ",";
                }
            }
            repairIdsName = repairIdsName.substring(0, repairIdsName.lastIndexOf(","));
            orderService.assignRepairs(order, repairIds, repairIdsName);
        }
            orderService.updateRepairByorderId(orderId,repairId,user);



        return null;
    }

   /* *//**
     * 分配订单
     *
     * @param orderId
     * @param repairId
     * @return
     *//*
    @RequiresPermissions("order:assign")
    @RequestMapping(value = "assignRepair", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "分配订单")
    public String assignRepair(@RequestParam(value = "orderId", defaultValue = "") Long orderId,
                               @RequestParam(value = "repairId", defaultValue = "") Long repairId) {
        if (CommonButil.isEmpty(orderId) || CommonButil.isEmpty(repairId)) {
            return "请求参数错误";
        }

        User user = userService.getUser(getCurrentUserId());
        if (CommonButil.isEmpty(user)) {
            return "登录超时，请重新登录";
        }
        // 判断是否是经理
        if (2 != user.getUserType() && 3 != user.getUserType()) {
            return "非法操作，请重新再试";
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.findOne(orderId);
        if (null == order) {
            return "该订单不存在，请重新再试";
        }

        // 只有在待处理和已接单情况下 才能分配订单
        if (0 != order.getOrderState() && 1 != order.getOrderState()) {
            return "分配失败，该订单已完成/已取消";
        }

        // 获取要分配的工程师
        User repair = userService.getUser(repairId);
        if (null == repair) {
            return "该工程师不存在，请重新再试";
        }

        // 判断repairId 是否是工程师
        if (1 != repair.getUserType() && 2 != repair.getUserType()) {
            return "非工程师不能接单，请重新再试";
        }

        // 修改订单状态为：已接单
        orderService.assignRepair(order, repair, user);
        return null;
    }*/

    /**
     * 接受订单
     *
     * @param orderId
     * @return
     */
    @RequiresPermissions("order:accept")
    @RequestMapping(value = "acceptOrder", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "工程师接单")
    public String acceptOrder(@RequestParam(value = "orderId", defaultValue = "") Long orderId) {
        if (CommonButil.isEmpty(orderId)) {
            return "请求参数错误";
        }

        User user = userService.getUser(getCurrentUserId());
        if (CommonButil.isEmpty(user)) {
            return "登录超时，请重新登录";
        }
        // 判断是否是工程师
        if (1 != user.getUserType()) {
            return "非法操作，请重新再试";
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.findOne(orderId);
        if (null == order) {
            return "该订单不存在，请重新再试";
        }

        // 只有在待处理和已接单情况下 才能分配订单
        if (0 != order.getOrderState()) {
            return "接单失败，该订单已被受理";
        }

        // 修改订单状态为：已接单
        orderService.acceptOrder(order, user);
        return null;
    }

    /**
     * 完成订单
     *
     * @param orderId
     * @param orderExplain
     * @return
     */
    @RequiresPermissions("order:complete")
    @RequestMapping(value = "completeOrder", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "工程师完成订单")
    public String completeOrder(@RequestParam(value = "orderId", defaultValue = "") Long orderId,
                                @RequestParam(value = "orderExplain", defaultValue = "") String orderExplain) {
        if (CommonButil.isEmpty(orderId)) {
            return "请求参数错误";
        }

        User user = userService.getUser(getCurrentUserId());
        if (CommonButil.isEmpty(user)) {
            return "登录超时，请重新登录";
        }
        // 判断是否是工程师
        if (1 != user.getUserType() && 2 != user.getUserType()) {
            return "非法操作，请重新再试";
        }
        // 根据订单id查找订单
        RepairOrder order = orderService.findOne(orderId);
        if (null == order) {
            return "该订单不存在，请重新再试";
        }

        // 只有在待处理和已接单情况下 才能分配订单
        if (1 != order.getOrderState() || user.getId() != order.getRepairId()) {
            return "非法操作，请重新再试";
        }
        // 修改订单状态为：完成订单 、保存处理说明
        orderService.completeOrder(order, user, orderExplain);
        return null;
    }

    /**
     * 评论订单
     *
     * @param commentStar    评星
     * @param commentContent 评论
     * @param orderId
     * @return
     */
    @RequiresPermissions("order:comment")
    @RequestMapping(value = "commentOrder", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "用户评价订单")
    public String commentOrder(@RequestParam(value = "commentStar", defaultValue = "") String commentStar,
                               @RequestParam(value = "commentContent", defaultValue = "") String commentContent,
                               @RequestParam(value = "orderId", defaultValue = "") Long orderId) {
        if (CommonButil.isEmpty(commentStar) || CommonButil.isEmpty(orderId)) {
            return "请求参数错误";
        }
        // 获取当前用户
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            return "用户登录超时，请重新登录";
        }

        RepairOrder order = orderService.findOne(orderId);
        if (null == order) {
            return "该订单不存在，请重新再试";
        }
        // 只有订单完成的时候才能评价 并且是自己的订单
        if (2 != order.getOrderState() || user.getId() != order.getUserId()) {
            return "非法操作，请重新再试";
        }

        // 保存评价
        RepairOrderLog rol = orderService.saveComment(order, commentStar, commentContent, user);
        if (null == rol) {
            return "提交失败，请稍后再试";
        }

        return null;
    }

    /**
     * 发布订单页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("order:create")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            model.addAttribute("message", "登录超时，请重新再试");
            return "order/orderForm";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("repairTypeList", mapper.writeValueAsString(wordService.findByType(Consts.REPAIR_TYPE)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RepairOrder order = new RepairOrder();
        order.setContactUser(user.getUserName());
        order.setContactPhone(user.getLoginName());
        order.setContactAddress(user.getUserAddress());
       /* List<String> typeList=orderCostService.findAllOrderType();
        model.addAttribute("orderType", typeList);*/
        model.addAttribute("order", order);
        model.addAttribute("index", "order/list");
        model.addAttribute("title", "发布");
        model.addAttribute("action", "create");
        model.addAttribute("tableType", resourceService.findTabeType("order/list"));
        // 获取所有工程师和经理的设备id推送
        List<String> repairDeviceIds = orderService.getAllRepairDeviceId();
        if (!CommonButil.isEmpty(repairDeviceIds)) {
            String repairTypeStr = wordService.findByIndex(order.getRepairType(),"REPAIR_TYPE");
            String content = "用户 " + user.getUserName() + " 发布了一个 " + repairTypeStr + " 的订单，请注意查看！";
            SendPush.pushToUser(repairDeviceIds, content, "", "1", order.getId().toString());
        }
        return "order/orderForm";
    }

    @RequestMapping(value = "ajaxGetAllCost")
    @ResponseBody
    public void ajaxGetAllCost(HttpServletResponse response, Model model,
                               String typeId) {
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            model.addAttribute("message", "登录超时，请重新登录");
        }
        //根据类型ID查找所有费用集合
        List<OrderTypeCostType> orderTypeCostTypeList = orderCostService.getAllCostByTypeId(typeId);
        WebUtil.printJson(response, JsonUtil.obj2Json(new Result(true).data(orderTypeCostTypeList),
                "isLocked", "rewardRatio", "hasReward", "createTime", "updateTime"));
    }

    /**
     * 用户发布订单
     *
     * @param request
     * @param repairType     订单类型
     * @param contactUser    联系人
     * @param contactPhone   联系电话
     * @param contactAddress 联系地址
     * @param orderDesc      订单描述
     * @param imgStrs        图片base64字符串
     * @return
     */
    @RequiresPermissions("order:create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @SystemControllerLog(description = "发布订单")
    public String create(HttpServletRequest request, RedirectAttributes redirectAttributes,
                         @RequestParam(value = "repairType", defaultValue = "0") Integer repairType,
                         @RequestParam(value = "orderFile", defaultValue = "") String orderFile,
                         @RequestParam(value = "contactUser", defaultValue = "") String contactUser,
                         @RequestParam(value = "contactPhone", defaultValue = "") String contactPhone,
                         @RequestParam(value = "contactAddress", defaultValue = "") String contactAddress,
                         @RequestParam(value = "qty", defaultValue = "") Integer qty,
                         @RequestParam(value = "orderDesc", defaultValue = "") String orderDesc,
                         @RequestParam(value = "imgStrs", defaultValue = "") String imgStrs) {
        String money[] = request.getParameterValues("money");
        String costType[] = request.getParameterValues("costType");
        if (CommonButil.isEmpty(contactUser) || CommonButil.isEmpty(contactPhone) || CommonButil.isEmpty(contactAddress)
                || CommonButil.isEmpty(orderDesc)) {
            redirectAttributes.addFlashAttribute("message", "请求参数错误");
            return "redirect:/order/list";
        }
        if (CommonButil.isEmpty(costType)&&repairType == 1){
            redirectAttributes.addFlashAttribute("message", "添加失败！安装实施费用项不可为空");
            return "redirect:/order/list";
        }
        List<String> list = new LinkedList<String>();
        if (repairType == 1 && costType.length > 0 && money.length > 0) {
            for (int i = 0; i < costType.length; i++) {
                if (!list.contains(costType[i])) {
                    list.add(costType[i]);
                } else {
                    redirectAttributes.addFlashAttribute("message", "单项费用不可重复添加！");
                    return "redirect:/order/list";
                }
            }
            if (costType.length != money.length) {
                redirectAttributes.addFlashAttribute("message", "请输入费用");
                return "redirect:/order/list";
            }
        }

        // 获取当前用户
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            redirectAttributes.addFlashAttribute("message", "操作错误，请重新再试");
            return "redirect:/order/list";
        }
        String orderImgs = "";
        String orderImgsThumb = "";
        if (!CommonButil.isEmpty(imgStrs)) {
            String userFolder = folder + user.getId() + "/";
            List<String> imgUrlList = UploadImgButil.decodeBase64ToImage(imgStrs.split(","), userFolder, targetWidth,
                    targetHeight);
            if (!CommonButil.isEmpty(imgUrlList)) {
                for (int i = 0; i < imgUrlList.size(); i++) {
                    String imgUrl = imgUrlList.get(i);
                    String[] arr = imgUrl.split(",");
                    if (arr.length == 2) {
                        orderImgs += (("".equals(orderImgs) ? "" : ",") + arr[0]);
                        orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + arr[1]);
                    } else {
                        orderImgs += (("".equals(orderImgs) ? "" : ",") + null);
                        orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + null);
                    }
                }
            }
        }
        // 保存订单
        RepairOrder savedOrder = orderService.saveOrder(repairType, contactUser, contactPhone, contactAddress, qty,
                orderDesc, orderImgs, orderImgsThumb, user);
        if (repairType == 1 &&!CommonButil.isEmpty(costType)&&costType.length > 0 && money.length > 0) {
            for (int i = 0; i < money.length; i++) {
                OrderCost orderCost = new OrderCost();
                orderCost.setOrderId(savedOrder.getId());
                orderCost.setCost(Double.parseDouble(money[i]));
                orderCost.setCreateTime(CommonButil.getNowTime());
                OrderCostType orderCostType = new OrderCostType();
                orderCostType.setId(Long.parseLong(costType[i]));
                orderCost.setCostType(orderCostType);
                orderCostService.save(orderCost);

            }
        }
        if (null == savedOrder) {
            redirectAttributes.addFlashAttribute("message", "发布失败，请重新再试");
            return "redirect:/order/list";
        }
        /*插入上传文件信息*/
        orderService.updateByOrderd(savedOrder.getId(), orderFile);
        redirectAttributes.addFlashAttribute("message", "发布成功");
        return "redirect:/order/list";
    }

    /**
     * 订单修改页面
     *
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("order:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(Model model, @PathVariable("id") Long id) {
        User user = userService.getUser(getCurrentUserId());
        if (CommonButil.isEmpty(user)) {
            model.addAttribute("message", "登录超时，请重新登录");
            return "order/orderForm";
        }
        RepairOrder order = orderService.getOrderById(id);
        if (order.getUserId() != user.getId()) {
            model.addAttribute("message", "非法操作，请重新再试");
            return "order/orderForm";
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("repairTypeList", mapper.writeValueAsString(wordService.findByType(Consts.REPAIR_TYPE)));
            model.addAttribute("fileList", orderService.findFilesByOrderId(id));
            /*取得所有订单相关费用*/
            List<OrderCost> orderCostList = orderService.findByOrderIdOrderByIdAsc(id);
            List<OrderTypeCostType> orderTypeCostTypeList = orderCostService.getAllCostByTypeId(order.getRepairType().toString());
            List<OrderCostType> orderCostTypesFromType = new ArrayList<OrderCostType>();
            List<OrderCostType> hasOrderCostTypesFromType = new ArrayList<OrderCostType>();
            for (OrderTypeCostType orderTypeCostType : orderTypeCostTypeList) {
                orderCostTypesFromType.add(orderTypeCostType.getOrderCostType());

            }
            for (OrderCost orderCost : orderCostList) {
                List<OrderCostType> orderCostTypeList = new ArrayList<OrderCostType>();
                for (OrderCostType orderCostType : orderCostTypesFromType) {
                    orderCostTypeList.add(orderCostType);
                }
                orderCost.setOrderCostTypes(orderCostTypeList);
                if (orderCostTypesFromType.contains(orderCost.getCostType())) {
                    ok:
                    for (int i = 0; i < orderCostTypesFromType.size(); i++) {
                        if (orderCostTypesFromType.get(i).getId() == orderCost.getCostType().getId()) {
                            orderCostTypesFromType.remove(i);
                            break ok;
                        }
                    }
                }
            }
            model.addAttribute("costList", orderCostList);
           /* model.addAttribute("costTypeList",orderCostService.getAllCostByTypeId(order.getRepairType().toString()));*/
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("orderImgs",
                CommonButil.isEmpty(order.getOrderImgs()) ? null : order.getOrderImgs().split(","));
        model.addAttribute("order", order);
        model.addAttribute("index", "order/list");
        model.addAttribute("title", "修改");
        model.addAttribute("action", "update");
        model.addAttribute("tableType", resourceService.findTabeType("order/list"));
        return "order/orderForm";
    }

    /**
     * 用户修改订单
     *
     * @param repairType     订单类型
     * @param contactUser    联系人
     * @param contactPhone   联系电话
     * @param contactAddress 联系地址
     * @param orderDesc      订单描述
     * @param imgStrs        图片base64字符串
     * @param orderId        订单ID
     * @return
     */
    @RequiresPermissions("order:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @SystemControllerLog(description = "更新订单")
    public String update(HttpServletRequest request,RedirectAttributes redirectAttributes,
                         @RequestParam(value = "repairType", defaultValue = "0") Integer repairType,
                         @RequestParam(value = "contactUser", defaultValue = "") String contactUser,
                         @RequestParam(value = "orderFile", defaultValue = "") String orderFile,
                         @RequestParam(value = "contactPhone", defaultValue = "") String contactPhone,
                         @RequestParam(value = "contactAddress", defaultValue = "") String contactAddress,
                         @RequestParam(value = "orderDesc", defaultValue = "") String orderDesc,
                         @RequestParam(value = "imgStrs", defaultValue = "") String imgStrs,
                         @RequestParam(value = "orderId", defaultValue = "") Long orderId) {
        if (CommonButil.isEmpty(contactUser) || CommonButil.isEmpty(contactPhone) || CommonButil.isEmpty(contactAddress)
                || CommonButil.isEmpty(orderDesc) || CommonButil.isEmpty(orderId)) {
            redirectAttributes.addFlashAttribute("message", "请求参数错误");
            return "redirect:/order/list";
        }
        String money[] = request.getParameterValues("money");
        String costType[] = request.getParameterValues("costType");
        if (CommonButil.isEmpty(costType)&&repairType == 1){
            redirectAttributes.addFlashAttribute("message", "添加失败！安装实施费用项不可为空");
            return "redirect:/order/list";
        }
        List<String> list = new LinkedList<String>();
        if (repairType == 1 && costType.length > 0 && money.length > 0) {
            for (int i = 0; i < costType.length; i++) {
                if (!list.contains(costType[i])) {
                    list.add(costType[i]);
                } else {
                    redirectAttributes.addFlashAttribute("message", "单项费用不可重复添加！");
                    return "redirect:/order/list";
                }
            }
            if (costType.length != money.length) {
                redirectAttributes.addFlashAttribute("message", "请输入费用");
                return "redirect:/order/list";
            }
        }
        // 获取当前用户
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            redirectAttributes.addFlashAttribute("message", "操作错误，请重新再试");
            return "redirect:/order/list";
        }
        RepairOrder order = orderService.getOrderById(orderId);
        if (null == order) {
            redirectAttributes.addFlashAttribute("message", "订单不存在，请重新再试");
            return "redirect:/order/list";
        }
        if (user.getId() != order.getUserId()) {
            redirectAttributes.addFlashAttribute("message", "非法操作，请重新再试");
            return "redirect:/order/list";
        }

        if (0 != order.getOrderState()) {
            redirectAttributes.addFlashAttribute("message", "修改失败，该订单已被受理");
            return "redirect:/order/list";
        }

        String orderImgs = "";
        String orderImgsThumb = "";
        if (!CommonButil.isEmpty(imgStrs)) {
            String userFolder = folder + user.getId() + "/";
            List<String> imgUrlList = UploadImgButil.decodeBase64ToImage(imgStrs.split(","), userFolder, targetWidth,
                    targetHeight);
            if (!CommonButil.isEmpty(imgUrlList)) {
                for (int i = 0; i < imgUrlList.size(); i++) {
                    String imgUrl = imgUrlList.get(i);
                    String[] arr = imgUrl.split(",");
                    if (arr.length == 2) {
                        orderImgs += (("".equals(orderImgs) ? "" : ",") + arr[0]);
                        orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + arr[1]);
                    } else {
                        orderImgs += (("".equals(orderImgs) ? "" : ",") + null);
                        orderImgsThumb += (("".equals(orderImgsThumb) ? "" : ",") + null);
                    }
                }
            }
        }
        // 修改订单
        RepairOrder savedOrder = orderService.updateOrder(order, repairType, contactUser, contactPhone, contactAddress,
                orderDesc, orderImgs, orderImgsThumb);
        if (repairType == 1 &&!CommonButil.isEmpty(costType)&&costType.length > 0 && money.length > 0) {
            orderCostService.delOrderCostByOId(orderId);
            for (int i = 0; i < money.length; i++) {
                OrderCost orderCost = new OrderCost();
                orderCost.setOrderId(savedOrder.getId());
                orderCost.setCost(Double.parseDouble(money[i]));
                orderCost.setCreateTime(CommonButil.getNowTime());
                OrderCostType orderCostType = new OrderCostType();
                orderCostType.setId(Long.parseLong(costType[i]));
                orderCost.setCostType(orderCostType);
                orderCostService.save(orderCost);

            }
        }
        if (null == savedOrder) {
            redirectAttributes.addFlashAttribute("message", "修改失败，请重新再试");
            return "redirect:/order/list";
        }
        redirectAttributes.addFlashAttribute("message", "修改成功");
        /*插入上传文件信息*/
        orderService.updateByOrderd(savedOrder.getId(), orderFile);
        return "redirect:/order/list";
    }

    /**
     * 取消订单
     *
     * @param redirectAttributes
     * @param orderId
     * @return
     */
    @RequiresPermissions("order:cancel")
    @RequestMapping(value = "cancel/{id}")
    @SystemControllerLog(description = "取消订单")
    public String update(RedirectAttributes redirectAttributes,
                         @PathVariable("id") Long orderId) {
        if (CommonButil.isEmpty(orderId)) {
            redirectAttributes.addFlashAttribute("message", "请求参数错误");
            return "redirect:/order/list";
        }
        // 获取当前用户
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            redirectAttributes.addFlashAttribute("message", "非法操作，请重新再试");
            return "redirect:/order/list";
        }
        RepairOrder order = orderService.getOrderById(orderId);
        if (null == order) {
            redirectAttributes.addFlashAttribute("message", "订单不存在，请重新再试");
            return "redirect:/order/list";
        }
        if (user.getId() != order.getUserId()) {
            redirectAttributes.addFlashAttribute("message", "非法操作，请重新再试");
            return "redirect:/order/list";
        }
        if (0 != order.getOrderState()) {
            redirectAttributes.addFlashAttribute("message", "取消失败，该订单已被受理");
            return "redirect:/order/list";
        }
        redirectAttributes.addFlashAttribute("message", "订单编号：" + order.getOrderNum() + " 已取消成功");
        orderService.cancelOrder(order, user);
        return "redirect:/order/list";
    }

    @RequestMapping(value = "ajaxOrderDetail")
    @ResponseBody
    public void ajaxOrderDetail(HttpServletResponse response, Model model,
                                Long orderId) {
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            model.addAttribute("message", "登录超时，请重新登录");
        }
        //查找订单
        RepairOrder repairOrder = orderService.findOne(orderId);
        //查字典
        List<WordBook> wordBooksForState=wordService.findByType(Consts.ORDER_STATE);//订单状态
        for (WordBook wordBook:wordBooksForState){
            if (repairOrder.getOrderState()==wordBook.getWordIndex()){
                repairOrder.setCcIds(wordBook.getWordValue());
            }
        }
        List<WordBook> wordBooksForType=wordService.findByType(Consts.REPAIR_TYPE);//订单类型
        for (WordBook wordBook:wordBooksForType){
            if (repairOrder.getRepairType()==wordBook.getWordIndex()){
                repairOrder.setOrderExplain(wordBook.getWordValue());
            }
        }
        repairOrder.setCommentContent(repairOrder.getRepair().getUserName());
        //查找相关人信息
        List<User> userList = orderEngineerService.findEngineerByOrderId(orderId);
        repairOrder.setOrderEngineerList(userList);
        //查询订单相关费用
        List<OrderCost> orderCostList = orderCostService.findByOrderIdOrderByIdAsc(orderId);
        repairOrder.setOrderCostList(orderCostList);
        WebUtil.printJson(response, JsonUtil.obj2Json(new Result(true).data(repairOrder), "user", "hasComment", "repair", "completionTime",
                 "commentStar", "orderImgs", "orderImgsThumb", "updateTime", "contactLocation","appointmentTime","hasAgain",
                 "isRead", "orderSource","qty"));
    }

    @RequestMapping(value = "saveCostEngineer")
    @ResponseBody
    public void saveCostEngineer(HttpServletResponse response,
                                 @RequestParam(value = "orderId", defaultValue = "") Long orderId,
                                 @RequestParam(value = "orderTypes", defaultValue = "") String costTypes,
                                 @RequestParam(value = "repairIds", defaultValue = "") String repairIds,
                                 @RequestParam(value = "costs", defaultValue = "") String costs) {
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(false).msg("登录超时，请重新登录")));
        }
        int result = orderService.saveCostEngineer(orderId, costTypes, repairIds, costs);
        if (result > 0) {
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(true).data(result).msg("分配成功!")));
        } else {
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(false).msg("操作失败!")));
        }

    }

    /**
     * 文件上传
     *
     * @param request
     * @param response
     * @param file
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public void upload(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("file") CommonsMultipartFile file) throws IOException {
       /*判断文件是否为空*/
        if (file != null && !file.isEmpty()) {
            Random random = new Random();
            String fileName = "" + random.nextInt(10)
                    + System.currentTimeMillis();
            FileBo fileBo = UploadFileUtils.saveWordOdExcel(file, fileName);
            /*保存文件信息到数据库*/
            UploadFileOrders uploadFileOrders = orderService.saveUploadFile(0L, fileBo, file.getOriginalFilename());
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(true).msg("上传成功").data(uploadFileOrders)));
        } else {
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(false).msg("登录超时，请重新登录")));
        }
    }

    @RequestMapping(value = "ajaxDeleteFile")
    @ResponseBody
    public void ajaxDeleteFile(HttpServletRequest request, HttpServletResponse response, Model model,
                               Long id) {
        User user = (User) request.getSession().getAttribute("currentUser");
        if (null == user) {
            model.addAttribute("message", "登录超时，请重新登录");
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(orderService.deleteFile(id))) {
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(false).msg("文件不存在")));
        } else {
            WebUtil.printJson(response, JsonUtil.obj2Json(new Result(true).msg("删除成功")));
        }
    }
    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @param path
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    @ResponseBody
    public void onload(HttpServletRequest request, HttpServletResponse response,
                       @RequestParam("path") String path) throws IOException {

        String allpath=UploadFileUtils.getPathProperties("config","file.path")+path;

        File file = new File(allpath);// path是根据日志路径和文件名拼接出来的
        String filename = file.getName();// 获取日志文件名称
        InputStream fis = new BufferedInputStream(new FileInputStream(allpath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        os.write(buffer);// 输出文件
        os.flush();
        os.close();
    }

    /**
     * 取出Shiro中的当前用户Id.
     */
    private Long getCurrentUserId() {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user.id;
    }
}
