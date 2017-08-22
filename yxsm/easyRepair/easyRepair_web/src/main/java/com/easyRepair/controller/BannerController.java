package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.BannerService;
import com.easyRepair.service.NewsService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Banner;
import com.easyRepair.tabEntity.News;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private BannerService bannerService;

    @SystemControllerLog(description = "进入轮播图界面")
    @RequiresPermissions("banner:view")
    @RequestMapping("/list")
    public String banner(){
        return "banner/list";
    }

    @RequiresPermissions("banner:view")
    @RequestMapping(value = "/page")
    public void bannerPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<Banner> page = bannerService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //新增Banner对象
    @SystemControllerLog(description = "创建轮播图")
    @RequiresPermissions("banner:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void createBanner(Banner banner, Long newsId, HttpServletResponse response) {
        try {
            banner.setNews(newsService.getById(newsId));
            banner.setCreateTime(new Date());
            bannerService.update(banner);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    //修改Banner对象
    @SystemControllerLog(description = "修改轮播图")
    @RequiresPermissions("banner:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateBanner(Banner banner, Long newsId, HttpServletResponse response) {
        try {
            Banner banner2=bannerService.getById(banner.getId());
            banner2.setUrl(banner.getUrl());
            banner2.setUpdateTime(new Date());
            banner2.setSort(banner.getSort());
            banner2.setShows(banner.isShows());
            banner2.setNews(newsService.getById(newsId));
            banner2.setTitle(banner.getTitle());
            banner2.setContent(banner.getContent());
            bannerService.update(banner2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除轮播图")
    @RequiresPermissions("banner:delete")
    @RequestMapping("/delete")
    public void deleteBanner(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            bannerService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequiresPermissions("banner:view")
    @RequestMapping("/findNews")
    @ResponseBody
    public News findNews(Long id) {
        Banner banner=bannerService.getById(id);
        News news=newsService.getById(banner.getNews().getId());
        return news;
    }

    @RequiresPermissions("banner:view")
    @RequestMapping("/allNews")
    @ResponseBody
    public List<News> allNews() {
        List<News> newses = newsService.findByIsShow();
        return newses;
    }
}
