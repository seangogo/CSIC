package com.dh.web.order;

/**
 * Created by Coolkid on 2016/10/13.
 */

import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.RepairOrder;
import com.dh.entity.User;
import com.dh.service.account.AccountService;
import com.dh.service.account.ShiroDbRealm;
import com.dh.service.order.OrderService;
import com.dh.service.system.ResourceService;
import com.dh.service.system.WordBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 订单费用分配Controller
 */
@Controller
@RequestMapping(value = "/orderCost")
public class OrderCostController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private WordBookService wordService;
    @Autowired
    private AccountService userService;
    @Autowired
    private ResourceService resourceService;

    @RequiresPermissions("orderCost:allot")
    @RequestMapping(value = "feedeal")
    @SystemControllerLog(description = "分配订单费用")
    public String oflist(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
                         @RequestParam(value = "orderState", defaultValue = "") Integer orderState,
                         @RequestParam(value = "userName", defaultValue = "") String userName,
                         @RequestParam(value = "repairName", defaultValue = "") String repairName,
                         @RequestParam(value = "orderNum", defaultValue = "") String orderNum,
                         @RequestParam(value = "hasComplain", defaultValue = "") Integer hasComplain,
                         @RequestParam(value = "hasAgain", defaultValue = "") Integer hasAgain,
                         @RequestParam(value = "orderTime", defaultValue = "") String orderTime) {
        User user = userService.getUser(getCurrentUserId());
        if (null == user) {
            model.addAttribute("message", "登录超时，请重新登录");
            return "orderCost/orderfeeForm";
        }
        List<RepairOrder> orderList = orderService.findAllByState();
        model.addAttribute("orderList", orderList);
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("repairTypeList", mapper.writeValueAsString(wordService.findByType(Consts.REPAIR_TYPE)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("orderTime", orderTime);
        model.addAttribute("currentUser", user);
        model.addAttribute("orderNum", orderNum);
        model.addAttribute("hasAgain", hasAgain);
        model.addAttribute("hasComplain", hasComplain);
        model.addAttribute("orderState", orderState);
        model.addAttribute("userName", userName);
        model.addAttribute("repairName", repairName);
        model.addAttribute("index", "orderCost/feedeal");
        model.addAttribute("tableType", resourceService.findTabeType("order/list"));
        return "orderCost/orderfeeForm";
    }
    /**
     * 取出Shiro中的当前用户Id.
     */
    private Long getCurrentUserId() {
        ShiroDbRealm.ShiroUser user = (ShiroDbRealm.ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user.id;
    }

}
