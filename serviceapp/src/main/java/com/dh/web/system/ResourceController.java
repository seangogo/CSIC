package com.dh.web.system;

import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.Resource;
import com.dh.service.system.ResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 菜单管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/resource")
public class ResourceController {

	@Autowired
	private ResourceService resourceService;
	
	private final static String RESOURCE_TYPE = "1";
	
	@RequiresPermissions("resource:view")
	@RequestMapping()
	@SystemControllerLog(description = "查看菜单列表")
	public String getResources(@RequestParam(value = "type",defaultValue = RESOURCE_TYPE) String type,Model model) {
		List<Resource> resources = resourceService.findResourcesByType(type);
		model.addAttribute("resources", resources);
		model.addAttribute("type", type);
		model.addAttribute("index", "system/resource");
		model.addAttribute("tableType", resourceService.findTabeType("system/resource"));
		ObjectMapper mapper = new ObjectMapper();
		try {
			model.addAttribute("resourcesJson", mapper.writeValueAsString(resources));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "system/resource";
	}
	
	@RequiresPermissions("resource:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@SystemControllerLog(description = "更新菜单")
	public String update(HttpServletRequest request,@RequestParam(value = "type",defaultValue = RESOURCE_TYPE) String type,
			@Valid @ModelAttribute("resource") Resource resource, RedirectAttributes redirectAttributes) {
		resourceService.updateResource(request,resource);
        redirectAttributes.addFlashAttribute("message", "修改成功");
		return "redirect:/system/resource?type="+type;
	}
	
	@RequiresPermissions("resource:create")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@SystemControllerLog(description = "新增菜单")
	public String create(@RequestParam(value = "type",defaultValue = RESOURCE_TYPE) String type,
			@Valid @ModelAttribute("resource") Resource resource, RedirectAttributes redirectAttributes) {
		resourceService.saveResource(resource);
		redirectAttributes.addFlashAttribute("message", "新增成功");
		return "redirect:/system/resource?type="+type;
	}
	
	@RequiresPermissions("resource:delete")
	@RequestMapping(value = "delete/{id}")
	@SystemControllerLog(description = "删除菜单")
	public String delete(HttpServletRequest request,@RequestParam(value = "type",defaultValue = RESOURCE_TYPE) String type,
			@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		String msg = resourceService.deleteResource(request,id);
		redirectAttributes.addFlashAttribute("message", msg);
		return "redirect:/system/resource?type="+type;
	}

}
