package com.dh.web.system;

import com.dh.entity.Group;
import com.dh.service.system.GroupService;
import com.dh.service.system.ResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 分组管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/group")
public class GroupController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private ResourceService resourceService;
	
	@RequiresPermissions("group:view")
	@RequestMapping(method = RequestMethod.GET)
	public String getGroups(Model model) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			model.addAttribute("groupsJson", mapper.writeValueAsString(groupService.findGroups()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		model.addAttribute("index", "system/group");
		model.addAttribute("tableType", resourceService.findTabeType("system/group"));
		return "system/group";
	}
	
	
	@RequiresPermissions("group:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public Boolean update(@Valid @ModelAttribute("group") Group group) {
		return groupService.updateGroup(group);
	}
	
	
	@RequiresPermissions("group:create")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Long create(@RequestParam(value = "id") Long groupId) {
		return groupService.createByPid(groupId);
	}
	
	@RequiresPermissions("group:delete")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam(value = "id") Long groupId) {
		return groupService.delete(groupId);
	}
}
