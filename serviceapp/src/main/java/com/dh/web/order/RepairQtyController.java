package com.dh.web.order;

import com.dh.common.CommonButil;
import com.dh.common.POIUtils;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.service.order.OrderService;
import com.dh.service.system.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * 设备维修统计报表Controller
 * 
 */
@Controller
@RequestMapping(value = "/repairQty")
public class RepairQtyController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ResourceService resourceService;

	/**
	 *  工程师维修数量报表
	 * @param model
	 * @param orderTimeStart
	 * @param orderTimeEnd
	 * @param repairName
	 * @return
	 */
	@RequiresPermissions("repairQty:view")
	@RequestMapping(value = "list")
	@SystemControllerLog(description = "查看工程师维修数量报表")
	public String rqlist(Model model,HttpServletRequest request,
                         @RequestParam(value = "orderTimeStart", defaultValue = "") String orderTimeStart,
                         @RequestParam(value = "orderTimeEnd", defaultValue = "") String orderTimeEnd,
					   @RequestParam(value = "repairName", defaultValue = "") String repairName) {
		List rqlist = orderService.getRepairOrderQty(CommonButil.StartEndtoList(orderTimeStart,orderTimeEnd),repairName);
		model.addAttribute("repairQtyList", rqlist);
		model.addAttribute("orderTimeStart", orderTimeStart);
		model.addAttribute("orderTimeEnd", orderTimeEnd);
		model.addAttribute("repairName", repairName);
		model.addAttribute("index", "repairQty/list");
		model.addAttribute("tableType", resourceService.findTabeType("repairQty/list"));
		return "repairQty/list";
	}

	@RequiresPermissions("repairQty:export")
	@RequestMapping("/export")
	public void download(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam(value = "orderTimeStart", defaultValue = "") String orderTimeStart,
                         @RequestParam(value = "orderTimeEnd", defaultValue = "") String orderTimeEnd,
						 @RequestParam(value = "repairName", defaultValue = "") String repairName) throws IOException {
		List eclist = orderService.getRepairOrderQty(CommonButil.StartEndtoList(orderTimeStart,orderTimeEnd),repairName);
		String fileName = "设备维修统计报表";
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map map = new HashMap<String, Object>();
        map.put("colkey", "user_name");
        map.put("name", "工程师");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "orderCount");
        map.put("name", "数量");
        map.put("hide", false);
		listMap.add(map);
		POIUtils.exportToExcel(response, listMap, eclist, fileName);
	}
}
