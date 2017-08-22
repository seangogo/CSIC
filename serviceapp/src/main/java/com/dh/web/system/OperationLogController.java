package com.dh.web.system;

import com.dh.common.CommonButil;
import com.dh.configure.Consts;
import com.dh.service.system.OperationLogService;
import com.dh.service.system.ResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 操作日志管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/log")
public class OperationLogController {
	 
	@Autowired
	private OperationLogService logService;
	@Autowired
	private ResourceService resourceService;

	@RequiresPermissions("log:view")
	@RequestMapping()
	public String getBannerList(Model model,
			@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "logTimeStart", defaultValue = "") String logTimeStart,
			@RequestParam(value = "logTimeEnd", defaultValue = "") String logTimeEnd,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "type", defaultValue = "0") Integer type,
			@RequestParam(value = "descriptionType", defaultValue = "") String descriptionType) {
		model.addAttribute("logs", logService.findLogList(pageNumber, Consts.PAGE_SIZE,CommonButil.StartEndtoList(logTimeStart,logTimeEnd),userName,description,descriptionType,type));
		ObjectMapper mapper = new ObjectMapper();
		try {
			model.addAttribute("descList", mapper.writeValueAsString(logService.findOperationDesc()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		model.addAttribute("logTimeStart", logTimeStart);
		model.addAttribute("logTimeEnd", logTimeEnd);
		model.addAttribute("userName", userName);
		if(CommonButil.isEmpty(descriptionType)){
			model.addAttribute("description", description);
		}
		model.addAttribute("descriptionType", descriptionType);
		model.addAttribute("type", type);
		model.addAttribute("index", "system/log");
		model.addAttribute("tableType", resourceService.findTabeType("system/log"));
		return "system/logList";
	}
}
