package com.dh.web.account;

import javax.validation.Valid;

import com.dh.entity.Role;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dh.common.CommonButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.User;
import com.dh.service.account.AccountService;
import com.dh.service.system.ResourceService;
import com.dh.service.system.WordBookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 管理员管理用户的Controller.
 * 
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController extends Consts {

	@Autowired
	private AccountService accountService;
	@Autowired
	private WordBookService wordService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 查看用户列表
	 * 
	 * @param model
	 * @param pageNumber
	 * @param loginName
	 * @param userName
	 * @param userType
	 * @param groupId
	 * @return
	 */
	@RequiresPermissions("user:view")
	@RequestMapping()
	@SystemControllerLog(description = "查看用户列表")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "userType", defaultValue = "") Integer userType,
			@RequestParam(value = "groupId", defaultValue = "") Integer groupId) {
		Page<User> users = accountService.getAllUser(pageNumber, PAGE_SIZE, loginName, userName, userType, groupId);
		model.addAttribute("users", users);
		ObjectMapper mapper = new ObjectMapper();
		try {
			model.addAttribute("repairTypeList", mapper.writeValueAsString(wordService.findByType(REPAIR_TYPE)));
			model.addAttribute("roleList",wordService.findByType(USER_TYPE));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// model.addAttribute("groups", groupservice.findGroups());
		model.addAttribute("loginName", loginName);
		model.addAttribute("userName", userName);
		model.addAttribute("userType", userType);
		model.addAttribute("groupId", groupId);
		model.addAttribute("defaultPwd", DEFAULT_PASSWORD);
		model.addAttribute("index", "admin/user");
		model.addAttribute("tableType", resourceService.findTabeType("admin/user"));
		return "account/adminUserList";
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:create")
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	@SystemControllerLog(description = "新增用户")
	public String createUser(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		if (null != accountService.findUserByLoginName(user.getLoginName())) {
			redirectAttributes.addFlashAttribute("message", "用户已存在");
			return "redirect:/admin/user";
		}
		Role role=new Role();
		if (user.getUserType()==0)role.setId(2L);
		else if (user.getUserType()==1)role.setId(4L);
		else if (user.getUserType()==2)role.setId(3L);
		else if (user.getUserType()==3)role.setId(1L);
		else if (user.getUserType()==4)role.setId(5L);
		else redirectAttributes.addFlashAttribute("message", "新增用户失败");
		user.setRole(role);
		User savedUser = accountService.createUser(user);
		if (null == savedUser) {
			redirectAttributes.addFlashAttribute("message", "新增用户失败");
		} else {
			redirectAttributes.addFlashAttribute("message", "新增用户 " + savedUser.getLoginName() + " 成功");
		}
		return "redirect:/admin/user";
	}

	/**
	 * 检查用户手机是否存在
	 * 
	 * @param model
	 * @param loginName
	 * @return
	 */
	@RequiresPermissions("user:view")
	@RequestMapping(value = "checkLoginName", method = RequestMethod.POST)
	@ResponseBody
	public String checkLoginName(Model model, @RequestParam(value = "loginName", defaultValue = "") String loginName) {
		if (null != accountService.findUserByLoginName(loginName)) {
			return "用户已存在,请重新输入";
		} else {
			return null;
		}
	}

	/**
	 * 根据id获取用户
	 *
	 * @param id
	 * @return
	 */
	@RequiresPermissions("user:view")
	@RequestMapping(value = "getUser")
	@ResponseBody
	@SystemControllerLog(description = "查看用户详情")
	public String getUser(@RequestParam(value = "userId", defaultValue = "") Long id) {
		return accountService.getUser(id).toString().replaceAll("null", "");
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:update")
	@RequestMapping(value = "updateUser")
	@SystemControllerLog(description = "更新用户")
	public String updateUser(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		if (CommonButil.isEmpty(user.getId()) || CommonButil.isEmpty(user.getLoginName())
				|| CommonButil.isEmpty(user.getUserType())) {
			redirectAttributes.addFlashAttribute("message", "非法操作，请重新再试");
			return "redirect:/admin/user";
		}
		accountService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "更新用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:delete")
	@RequestMapping(value = "delete/{id}")
	@SystemControllerLog(description = "删除用户")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		accountService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}

	/**
	 * 重置用户密码
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("user:resetPwd")
	@RequestMapping(value = "resetPwd/{id}")
	@SystemControllerLog(description = "重置用户密码")
	public String resetPwd(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		if (null == user) {
			redirectAttributes.addFlashAttribute("message", "用户不存在");
			return "redirect:/admin/user";
		}
		accountService.resetPwd(user);

		redirectAttributes.addFlashAttribute("message",
				"重置 " + user.getLoginName() + " 用户的密码成功，初始密码为:" + DEFAULT_PASSWORD);
		return "redirect:/admin/user";
	}

	/**
	 * 锁定/解锁用户
	 * 
	 * @param redirectAttributes
	 * @param userId
	 * @param type
	 * @return
	 */
	@RequiresPermissions("user:isLocked")
	@RequestMapping(value = "isLocked")
	@ResponseBody
	@SystemControllerLog(description = "锁定/解锁用户")
	public String isLocked(RedirectAttributes redirectAttributes,
			@RequestParam(value = "userId", defaultValue = "") Long userId,
			@RequestParam(value = "type", defaultValue = "") Integer type) {
		User user = accountService.getUser(userId);
		if (null == user) {
			return "用户不存在";
		}
		accountService.isLocked(user, type);
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return null;
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", accountService.getUser(id));
		}
	}
}
