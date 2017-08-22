package com.dh.web.system;

import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.service.system.FeedbackService;
import com.dh.service.system.ResourceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 意见反馈管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 意见反馈列表
	 * @param model
	 * @param pageNumber
	 * @return
	 */
	@RequiresPermissions("feedback:view")
	@RequestMapping()
	@SystemControllerLog(description = "查看意见反馈列表")
	public String getFeedback(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "userName", defaultValue = "") String userName) {
		model.addAttribute("feedbacks", feedbackService.findFeedbackList(pageNumber, Consts.PAGE_SIZE,loginName,userName));
		model.addAttribute("loginName", loginName);
		model.addAttribute("userName", userName);
		model.addAttribute("index", "system/feedback");
		model.addAttribute("tableType", resourceService.findTabeType("system/feedback"));
		return "feedback/list";
	}
	
	
	/**
	 * 删除意见反馈
	 *
	 * @param id
	 * @return
	 */
	@RequiresPermissions("feedback:delete")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@SystemControllerLog(description = "删除意见反馈记录")
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
		if (null == feedbackService.findOne(id)) {
			redirectAttributes.addFlashAttribute("message","该记录不存在");
			return "redirect:/system/feedback";
		}
		feedbackService.delete(id);
		redirectAttributes.addFlashAttribute("message","删除成功");
		return "redirect:/system/feedback";
	}
}
