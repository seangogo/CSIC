package com.dh.web.order;

import com.dh.common.CommonButil;
import com.dh.common.POIUtils;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.RepairOrderLog;
import com.dh.service.order.OrderService;
import com.dh.service.system.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/orderCount")
public class OrderCountController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ResourceService resourceService;
	
	/**
	 *  工程师订单统计报表
	 * @param model
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param repairName
	 * @return
	 */
	@RequiresPermissions("orderCount:view")
	@RequestMapping(value = "repairCountList")
	@SystemControllerLog(description = "查看工程师订单统计报表")
	public String list(Model model,HttpServletRequest request,
                       @RequestParam(value = "orderTimeStart", defaultValue = "") String orderTimeStart,
                       @RequestParam(value = "orderTimeEnd", defaultValue = "") String orderTimeEnd,
                       @RequestParam(value = "repairName", defaultValue = "") String repairName) {
		model.addAttribute("orderCountList", orderService.getRepairOrderCountList(CommonButil.StartEndtoList(orderTimeStart,orderTimeEnd),repairName));
		model.addAttribute("orderTimeStart", orderTimeStart);
		model.addAttribute("orderTimeEnd", orderTimeEnd);
		model.addAttribute("repairName", repairName);
		model.addAttribute("index", "orderCount/repairCountList");
		model.addAttribute("tableType", resourceService.findTabeType("orderCount/repairCountList"));
		return "orderCount/countList";
	}

    @RequiresPermissions("orderCount:export")
    @RequestMapping("/export")
    public void ocexport(HttpServletResponse response,
                         @RequestParam(value = "orderTimeStart", defaultValue = "") String orderTimeStart,
                         @RequestParam(value = "orderTimeEnd", defaultValue = "") String orderTimeEnd,
                         @RequestParam(value = "repairName", defaultValue = "") String repairName) throws IOException {
        List oclist = orderService.getRepairOrderCountList(CommonButil.StartEndtoList(orderTimeStart,orderTimeEnd),repairName);
        String fileName = "工程师订单统计报表";
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map map = new HashMap<String, Object>();
        map.put("colkey", "repairName");
        map.put("name", "工程师");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "orderCount");
        map.put("name", "完成订单数");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "comment");
        map.put("name", "评价数");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "praise");
        map.put("name", "好评数");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "bad");
        map.put("name", "差评数");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "complaint");
        map.put("name", "投诉数");
        map.put("hide", false);
        listMap.add(map);
        POIUtils.exportToExcel(response, listMap, oclist, fileName);
    }
	
	/**
	 *  工程师订单统计明细报表
	 * @param model
	 * @param orderTime
	 * @param id
	 * @return
	 */
	@RequiresPermissions("orderCount:view")
	@RequestMapping(value = "repairCountDetail/{id}")
	@SystemControllerLog(description = "查看工程师订单明细报表")
	public String list(Model model, 
			@RequestParam(value = "orderTime", defaultValue = "") String orderTime,
			@PathVariable("id") Long id) {
		List<RepairOrderLog> list = orderService.getRepairOrderCountDetail(orderTime,id);
		model.addAttribute("orderCountDetail", list);
		model.addAttribute("orderTime", orderTime);
		model.addAttribute("id", id);
		model.addAttribute("index", "orderCount/repairCountList");
		model.addAttribute("tableType", resourceService.findTabeType("orderCount/repairCountList"));
		return "orderCount/countDetail";
	}
}
