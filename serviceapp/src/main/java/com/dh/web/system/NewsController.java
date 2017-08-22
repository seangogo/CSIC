package com.dh.web.system;

import com.dh.common.CommonButil;
import com.dh.common.UploadImgButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.News;
import com.dh.service.system.NewsService;
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
 * 新闻管理Controller
 * 
 */
@Controller
@RequestMapping(value = "/system/news")
public class NewsController {

	@Autowired
	private NewsService newsService;
	@Autowired
	private ResourceService resourceService;

	private static String folder = "news/";

	@RequiresPermissions("news:view")
	@RequestMapping()
	@SystemControllerLog(description = "查看新闻列表")
	public String getNews(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber,
			@RequestParam(value = "newsTitle", defaultValue = "") String newsTitle,
			@RequestParam(value = "message", defaultValue = "") String message ) {
		model.addAttribute("news", newsService.findNewsList(pageNumber, newsTitle, Consts.PAGE_SIZE));
		model.addAttribute("index", "system/news");
		model.addAttribute("tableType", resourceService.findTabeType("system/news"));
		model.addAttribute("newsTitle", newsTitle);
		model.addAttribute("message", message);
		return "news/newsList";
	}

	@RequiresPermissions("news:create")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("index", "system/news");
		model.addAttribute("action", "create");
		model.addAttribute("tableType", resourceService.findTabeType("system/news"));
		return "news/newsForm";
	}

	@RequiresPermissions("news:create")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@SystemControllerLog(description = "新增新闻")
	public String create(RedirectAttributes redirectAttributes, @RequestParam(value = "newsTitle", defaultValue = "") String newsTitle,
			@RequestParam(value = "newsDesc", defaultValue = "") String newsDesc,
			@RequestParam(value = "newsImgStr", defaultValue = "") String newsImgStr,
			@RequestParam(value = "newsContent", defaultValue = "") String newsContent) {

		if (CommonButil.isEmpty(newsTitle)) {
			redirectAttributes.addFlashAttribute("message", "保存失败，标题不能为空");
			return "redirect:/system/create";
		}

		News savedNews = new News();
		savedNews.setCreateTime(CommonButil.getNowTime());
		savedNews.setIsShow(0);
		savedNews.setNewsContent(newsContent);
		savedNews.setNewsDesc(newsDesc);
		savedNews.setOrderBy(0);
		savedNews.setNewsTitle(newsTitle.replaceAll("<","").replaceAll(">","").replaceAll("\"","").replaceAll("\'","").trim());
		if (null == savedNews) {
			redirectAttributes.addFlashAttribute("message", "保存失败");
			return "news/create";
		}
		if (!CommonButil.isEmpty(newsImgStr)) {
			String imgUrl = UploadImgButil.decodeBase64ToImage(newsImgStr, folder + savedNews.getId() + "/");
			if (!CommonButil.isEmpty(imgUrl)) {
				savedNews.setNewsImg(imgUrl);
				newsService.save(savedNews);
			}
		}
		return "redirect:/system/news";
	}

	/**
	 * 查看新闻详情
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("news:view")
	@RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
	@SystemControllerLog(description = "查看新闻详情")
	public String detail(Model model, @PathVariable("id") Long id) {
		model.addAttribute("news", newsService.findNewsById(id));
		model.addAttribute("index", "system/news");
		model.addAttribute("tableType", resourceService.findTabeType("system/news"));
		return "news/newsDetail";
	}

	/**
	 * 查看新闻修改
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("news:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(Model model, @PathVariable("id") Long id) {
		News news= newsService.findNewsById(id);
		model.addAttribute("news",news);
		if(!CommonButil.isEmpty(news.getNewsImg())){
			model.addAttribute("imgeStr","noUpdate");
		}
		model.addAttribute("index", "system/news");
		model.addAttribute("tableType", resourceService.findTabeType("system/news"));
		model.addAttribute("action", "update");
		return "news/newsForm";
	}

	/**
	 * 修改新闻
	 *
	 * @param newsId
	 * @param newsTitle
	 * @param newsDesc
	 * @param newsImgStr
	 * @param newsContent
	 * @return
	 */
	@RequiresPermissions("news:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@SystemControllerLog(description = "更新新闻")
	public String update(RedirectAttributes redirectAttributes, @RequestParam(value = "newsId", defaultValue = "") Long newsId,
			@RequestParam(value = "newsTitle", defaultValue = "") String newsTitle,
			@RequestParam(value = "newsDesc", defaultValue = "") String newsDesc,
			@RequestParam(value = "newsImgStr", defaultValue = "") String newsImgStr,
			@RequestParam(value = "newsContent", defaultValue = "") String newsContent) {
		if (CommonButil.isEmpty(newsTitle) || CommonButil.isEmpty(newsId)) {
			redirectAttributes.addFlashAttribute("message", "请求参数错误");
			return "redirect:/system/update/"+newsId;
		}
		News news = newsService.findNewsById(newsId);
		if (null == news) {
			redirectAttributes.addFlashAttribute("message", "该记录不存在");
			return "redirect:/system/update/"+newsId;
		}
		String imgUrl = "";
		if (!CommonButil.isEmpty(newsImgStr)&&!newsImgStr.equals("noUpdate")) {
			imgUrl = UploadImgButil.decodeBase64ToImage(newsImgStr, folder + newsId + "/");
		}
		news.setNewsTitle(newsTitle.replaceAll("<","").replaceAll(">","").replaceAll("\'","")
				.replaceAll("\"",""));
		news.setNewsDesc(newsDesc);
		if(!CommonButil.isEmpty(imgUrl)&&!imgUrl.equals("noUpdate")){
			news.setNewsImg(imgUrl);
		}
		news.setNewsContent(newsContent);
		newsService.save(news);
		return "redirect:/system/news";
	}

	/**
	 * 删除新闻
	 *
	 * @param id
	 * @return
	 */
	@RequiresPermissions("news:delete")
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	@SystemControllerLog(description = "删除新闻")
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
		if (null == newsService.findNewsById(id)) {
			return "redirect:/system/news";
		}
		if (newsService.checkBannerHasNews(id)) {
			redirectAttributes.addFlashAttribute("message", "有Banner关联，不可删除");
			return "redirect:/system/news";
		}

		newsService.delete(id);
		return "redirect:/system/news";
	}
}
