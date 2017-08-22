package com.dh.web.system;

import com.dh.common.CommonButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.InviteCode;
import com.dh.entity.User;
import com.dh.service.system.InviteCodeService;
import com.dh.service.system.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * banner管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/inviteCode")
public class InviteCodeController {

	@Autowired
	private InviteCodeService inviteCodeService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 分页查询邀请码
	 * 
	 * @param model
	 * @param pageNumber
	 * @return
	 */
	@RequiresPermissions("inviteCode:view")
	@RequestMapping()
	@SystemControllerLog(description = "查看邀请码管理")
	public String getInviteCodeList(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber) {
		model.addAttribute("inviteCodes", inviteCodeService.findInviteCodeList(pageNumber, Consts.PAGE_SIZE));
		model.addAttribute("index", "system/inviteCode");
		model.addAttribute("tableType", resourceService.findTabeType("system/inviteCode"));
		return "system/inviteCode";
	}
	/**
	 * 生成邀请码
	 * 
	 * @param
	 * @return
	 */
	@RequiresPermissions("inviteCode:create")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@SystemControllerLog(description = "生成邀请码")
	@ResponseBody
	public String create(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("currentUser");
		if (null == user) {
			return "登录超时，请重新登录";
		}
		InviteCode ic = inviteCodeService.createInviteCode(user);
		if(CommonButil.isEmpty(ic)){
			return "生成失败";
		}
		return null;
	}
	
	/**
	 * 删除邀请码
	 * @param redirectAttributes
	 * @param id
	 * @return
	 */
	@RequiresPermissions("inviteCode:delete")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@SystemControllerLog(description = "删除字典记录")
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
		if (null == inviteCodeService.findOne(id)) {
			redirectAttributes.addFlashAttribute("message","该记录不存在");
			return "redirect:/system/inviteCode";
		}
		inviteCodeService.delete(id);
		redirectAttributes.addFlashAttribute("message","删除成功");
		return "redirect:/system/inviteCode";
	}
}
