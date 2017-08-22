package com.dh.web.system;

import com.dh.common.CommonButil;
import com.dh.common.UploadImgButil;
import com.dh.configure.Consts;
import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.Banner;
import com.dh.service.system.BannerService;
import com.dh.service.system.NewsService;
import com.dh.service.system.ResourceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * banner管理Controller
 */
@Controller
@RequestMapping(value = "/system/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private ResourceService resourceService;

    private static String folder = "banner/";

    @RequiresPermissions("banner:view")
    @RequestMapping()
    @SystemControllerLog(description = "查看幻灯片列表")
    public String getBannerList(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNumber) {
        model.addAttribute("banners", bannerService.findBannerList(pageNumber, Consts.PAGE_SIZE));
        model.addAttribute("index", "system/banner");
        model.addAttribute("tableType", resourceService.findTabeType("system/banner"));
        return "banner/bannerList";
    }

    /**
     * 设置banner是否展示
     *
     * @param model
     * @param isShow
     * @param id
     * @return
     */
    @RequiresPermissions("banner:setShow")
    @RequestMapping(value = "show", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "设置幻灯片是否展示")
    public String setIsShow(Model model, @RequestParam(value = "isShow", defaultValue = "") Integer isShow,
                            @RequestParam(value = "id", defaultValue = "") Long id) {
        model.addAttribute("index", "system/news");
        model.addAttribute("tableType", resourceService.findTabeType("system/news"));
        if (CommonButil.isEmpty(isShow) || CommonButil.isEmpty(id) || (isShow != 1 && isShow != 0)) {
            return "操作失败，请求参数错误";
        }
        if (1 == isShow) {
            if (Consts.BANNER_MAX_COUNT <= bannerService.findBannerShowCount()) {
                return "超过首页banner显示刷了，最多显示" + Consts.BANNER_MAX_COUNT + "条";
            }
        }
        Banner banner = bannerService.findBannerById(id);
        if (null == banner) {
            return "操作失败，该记录不存在";
        }
        banner.setIsShow(isShow);
        bannerService.save(banner);
        return null;
    }

    /**
     * 新增banner页面
     *
     * @param model
     * @return
     */
    @RequiresPermissions("banner:create")
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("news", mapper.writeValueAsString(newsService.findNewsTitleList()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("index", "system/banner");
        model.addAttribute("tableType", resourceService.findTabeType("system/banner"));
        model.addAttribute("showIsFull", Consts.BANNER_MAX_COUNT <= bannerService.findBannerShowCount() ? 1 : 0);
        model.addAttribute("action", "create");
        model.addAttribute("title", "新增");
        return "banner/bannerForm";
    }

    @RequiresPermissions("banner:create")
    @SystemControllerLog(description = "新增幻灯片")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(RedirectAttributes redirectAttributes, @RequestParam(value = "newsId", defaultValue = "") Long newsId,
                         @RequestParam(value = "orderBy", defaultValue = "0") Long orderBy,
                         @RequestParam(value = "isShow", defaultValue = "0") Integer isShow,
                         @RequestParam(value = "bannerType", defaultValue = "0") Integer bannerType,
                         @RequestParam(value = "bannerTargetUrl", defaultValue = "") String bannerTargetUrl,
                         @RequestParam(value = "bannerTitle", defaultValue = "") String bannerTitle,
                         @RequestParam(value = "bannerImgStr", defaultValue = "") String bannerImgStr) {
        if (CommonButil.isEmpty(bannerImgStr)) {
            redirectAttributes.addFlashAttribute("message", "保存失败，图片不能为空");
            return "banner/bannerForm";
        }
        Banner banner = new Banner();
        banner.setOrderBy(orderBy);
        banner.setIsShow(isShow);
        banner.setBannerTitle(bannerTitle);
        if (0 == bannerType) {
            banner.setNewsId(newsId);
            banner.setBannerTargetUrl(Consts.NEWS_CONTENT_URL + newsId);
        } else {
            banner.setBannerTargetUrl(bannerTargetUrl);
        }
        banner.setBannerType(bannerType);
        Banner savedBanner = bannerService.save(banner);
        if (null == savedBanner) {
            redirectAttributes.addFlashAttribute("message", "保存失败");
            return "banner/bannerForm";
        }
        if (!CommonButil.isEmpty(bannerImgStr)) {
            String imgUrl = UploadImgButil.decodeBase64ToImage(bannerImgStr, folder + savedBanner.getId() + "/");
            if (!CommonButil.isEmpty(imgUrl)) {
                savedBanner.setBannerImg(imgUrl);
                bannerService.save(savedBanner);
            } else {
                redirectAttributes.addFlashAttribute("message", "图片上传失败");
            }
        }
        return "redirect:/system/banner";
    }

    /**
     * 查看banner
     *
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("banner:view")
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    @SystemControllerLog(description = "查看幻灯片详情")
    public String detail(Model model, @PathVariable("id") Long id) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("news", mapper.writeValueAsString(newsService.findNewsTitleList()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("banner", bannerService.findBannerById(id));
        model.addAttribute("index", "system/banner");
        model.addAttribute("tableType", resourceService.findTabeType("system/banner"));
        return "banner/bannerDetail";
    }


    /**
     * 查看banner修改
     *
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("banner:update")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("banner", bannerService.findBannerById(id));
        ObjectMapper mapper = new ObjectMapper();
        try {
            model.addAttribute("news", mapper.writeValueAsString(newsService.findNewsTitleList()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("index", "system/banner");
        model.addAttribute("tableType", resourceService.findTabeType("system/banner"));
        model.addAttribute("action", "update");
        model.addAttribute("showIsFull", Consts.BANNER_MAX_COUNT <= bannerService.findBannerShowCount() ? 1 : 0);
        model.addAttribute("title", "修改");
        return "banner/bannerForm";
    }

    /**
     * 修改banner
     *
     * @param bannerId
     * @param newsId
     * @param orderBy
     * @param isShow
     * @param bannerType
     * @param bannerTargetUrl
     * @param bannerImgStr
     * @return
     */
    @RequiresPermissions("banner:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @SystemControllerLog(description = "更新幻灯片")
    public String update(RedirectAttributes redirectAttributes,
                         @RequestParam(value = "bannerId", defaultValue = "") Long bannerId,
                         @RequestParam(value = "newsId", defaultValue = "") Long newsId,
                         @RequestParam(value = "orderBy", defaultValue = "0") Long orderBy,
                         @RequestParam(value = "isShow", defaultValue = "0") Integer isShow,
                         @RequestParam(value = "bannerType", defaultValue = "0") Integer bannerType,
                         @RequestParam(value = "bannerTargetUrl", defaultValue = "") String bannerTargetUrl,
                         @RequestParam(value = "bannerTitle", defaultValue = "") String bannerTitle,
                         @RequestParam(value = "bannerImgStr", defaultValue = "") String bannerImgStr) {
        if (CommonButil.isEmpty(bannerImgStr)) {
            redirectAttributes.addFlashAttribute("message", "保存失败，图片不能为空");
            return "banner/bannerForm";
        }
        Banner banner = bannerService.findBannerById(bannerId);
        if (null == banner) {
            redirectAttributes.addFlashAttribute("message", "该记录不存在");
            return "redirect:/system/banner";
        }
        String imgUrl = "";
        if (!CommonButil.isEmpty(bannerImgStr) && !bannerImgStr.equals(banner.getBannerImg())) {
            imgUrl = UploadImgButil.decodeBase64ToImage(bannerImgStr, folder + banner.getId() + "/");
        }
        banner.setOrderBy(orderBy);
        banner.setIsShow(isShow);
        banner.setBannerType(bannerType);
        if (!CommonButil.isEmpty(imgUrl)) {
            banner.setBannerImg(imgUrl);
        }
        banner.setBannerTitle(bannerTitle);
        if (bannerType == 0) {
            banner.setBannerTargetUrl(Consts.NEWS_CONTENT_URL + newsId);
            banner.setNewsId(newsId);
        } else {
            banner.setBannerTargetUrl(bannerTargetUrl);
            banner.setNewsId(null);
        }
        bannerService.save(banner);
        return "redirect:/system/banner";
    }

    /**
     * 删除banner
     *
     * @param model
     * @param id
     * @return
     */
    @RequiresPermissions("banner:delete")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @SystemControllerLog(description = "删除幻灯片")
    public String delete(Model model, @PathVariable("id") Long id) {
        if (null == bannerService.findBannerById(id)) {
            return "redirect:/system/banner";
        }
        bannerService.delete(id);
        return "redirect:/system/banner";
    }
}
