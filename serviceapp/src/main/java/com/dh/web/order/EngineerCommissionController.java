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
 * 工程师提成报表Controller
 * 
 */
@Controller
@RequestMapping(value = "/engineerCommission")
public class EngineerCommissionController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ResourceService resourceService;
	

	/**
	 *  工程师提成报表
	 * @param model
	 * @param orderTimeStart
     * @param orderTimeEnd
	 * @param repairName
	 * @return
	 */
	@RequiresPermissions("engineerCommission:view")
	@RequestMapping(value = "list")
	@SystemControllerLog(description = "查看工程师提成报表")
	public String eclist(Model model,HttpServletRequest request,
					   @RequestParam(value = "orderTimeStart", defaultValue = "") String orderTimeStart,
                       @RequestParam(value = "orderTimeEnd", defaultValue = "") String orderTimeEnd,
					   @RequestParam(value = "repairName", defaultValue = "") String repairName) {
        List<?> eclist = orderService.getEngineerCommission(CommonButil.StartEndtoList(orderTimeStart,orderTimeEnd), repairName);
        model.addAttribute("engineerCommissionList", eclist);
        model.addAttribute("repairName", repairName);
        model.addAttribute("index", "engineerCommission/list");
        model.addAttribute("tableType", resourceService.findTabeType("engineerCommission/list"));
        model.addAttribute("orderTimeStart",orderTimeStart);
        model.addAttribute("orderTimeEnd",orderTimeEnd);
        return "engineerCommission/list";
    }
    /**
     *  excel导出工程师提成报表
     * @param orderTimeStart
     * @param orderTimeEnd
     * @param repairName
     * @return
     */
	@RequiresPermissions("engineerCommission:export")
	@RequestMapping("/export")
	public void download(HttpServletResponse response,
                         @RequestParam(value = "orderTimeStart", defaultValue = "") String orderTimeStart,
                         @RequestParam(value = "orderTimeEnd", defaultValue = "") String orderTimeEnd,
						 @RequestParam(value = "repairName", defaultValue = "") String repairName
                         ) throws IOException {
        List<?> eclist = orderService.getEngineerCommission(CommonButil.StartEndtoList(orderTimeStart,orderTimeEnd), repairName);
        String fileName = "工程师提成报表";

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map map = new HashMap<String, Object>();
        map.put("colkey", "order_num");
        map.put("name", "单据编号");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "user_name");
        map.put("name", "工程师");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "clf");
        map.put("name", "材料费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "gpf");
        map.put("name", "改配费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "byf");
        map.put("name", "搬运费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "azf");
        map.put("name", "安装费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "wxf");
        map.put("name", "外协费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "jtf");
        map.put("name", "交通费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "shf");
        map.put("name", "送货费");
        map.put("hide", false);
        listMap.add(map);
        map = new HashMap<String, Object>();
        map.put("colkey", "hj");
        map.put("name", "合计");
        map.put("hide", false);
        listMap.add(map);
        POIUtils.exportToExcel(response, listMap, eclist, fileName);
    }
}
