package com.dh.web.system;

import com.dh.common.CommonButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.User;
import com.dh.entity.WordBook;
import com.dh.service.system.ResourceService;
import com.dh.service.system.WordBookService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * banner管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/wordBook")
public class WordBookController {

	@Autowired
	private WordBookService wordBookService;
	@Autowired
	private ResourceService resourceService;

	/**
	 * 分页查询字典
	 * @param model
	 * @param pageNumber
	 * @param type
	 * @return
	 */
	@RequiresPermissions("wordBook:view")
	@RequestMapping()
	@SystemControllerLog(description = "查看字典管理")
	public String getBannerList(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "type", defaultValue = "") String type) {
		model.addAttribute("wordBooks", wordBookService.findWordBookList(pageNumber,Consts.PAGE_SIZE,type));
		model.addAttribute("type", type);
		model.addAttribute("page", pageNumber);
		model.addAttribute("index", "system/wordBook");
		model.addAttribute("tableType", resourceService.findTabeType("system/wordBook"));
		return "wordBook/wordBookList";
	}
	
	/**
	 * 新增/更新字典
	 * @param redirectAttributes
	 * @param request
	 * @param wordBook
	 * @return
	 */
	@RequiresPermissions("wordBook:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@SystemControllerLog(description = "新增/更新字典")
	public String create(RedirectAttributes redirectAttributes,HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@Valid @ModelAttribute("wordBook") WordBook wordBook) {
		User user = (User)request.getSession().getAttribute("currentUser");
		if (null == user) {
			redirectAttributes.addFlashAttribute("message", "登录超时，请重新登录");
			return "redirect:/system/wordBook?page="+pageNumber;
		}
		//判断该类型字典的key是否存在
		if(!CommonButil.isEmpty(wordBookService.findByIndex(wordBook.getWordIndex(), wordBook.getType(),wordBook.getId()))){
			redirectAttributes.addFlashAttribute("message", "操作失败，字典类型和键重复");
			return "redirect:/system/wordBook?page="+pageNumber;
		}
		
		wordBookService.saveWordBook(wordBook,user);
		redirectAttributes.addFlashAttribute("message", "操作成功");
		return "redirect:/system/wordBook?page="+pageNumber;
	}
	
	
	/**
	 * 查看字典详情
	 * @param redirectAttributes
	 * @param id
	 * @return
	 */
	@RequiresPermissions("wordBook:view")
	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@SystemControllerLog(description = "查看字典")
	@ResponseBody
	public WordBook detail(RedirectAttributes redirectAttributes,
			@RequestParam(value = "id", defaultValue = "") Long id) {
		return wordBookService.findOne(id);
	}
	
	
	
	/**
	 * 删除字典记录
	 * @param redirectAttributes
	 * @param id
	 * @return
	 */
	@RequiresPermissions("wordBook:delete")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@SystemControllerLog(description = "删除字典记录")
	public String delete(RedirectAttributes redirectAttributes, 
			@RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@PathVariable("id") Long id) {
		if (null == wordBookService.findOne(id)) {
			redirectAttributes.addFlashAttribute("message","该记录不存在");
			return "redirect:/system/wordBook?page="+pageNumber;
		}
		wordBookService.delete(id);
		redirectAttributes.addFlashAttribute("message","删除成功");
		return "redirect:/system/wordBook?page="+pageNumber;
	}
}
