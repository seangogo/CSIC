package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.BannerService;
import com.easyRepair.service.NewsService;
import com.easyRepair.service.NewsTypeService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Banner;
import com.easyRepair.tabEntity.News;
import com.easyRepair.tabEntity.NewsType;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by WJY on 2017/2/16.
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsTypeService newsTypeService;
    @Autowired
    private BannerService bannerService;


    //@SystemControllerLog(description = "进入新闻界面")
    @RequiresPermissions("news:view")
    @RequestMapping("/list")
    public String news(){
        return "news/list";
    }


    @SystemControllerLog(description = "查看新闻列表")
    @RequiresPermissions("news:view")
    @RequestMapping(value = "/page")
    public void newsPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<News> page = newsService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions("news:view")
    @RequestMapping("/allNewsType")
    @ResponseBody
    public List<NewsType> allNewsType(HttpServletResponse response) {
        List<NewsType> newsTypes=newsTypeService.findAll();
        return newsTypes;
        //WebUtil.print(response,new Result(true).msg("成功获取").data(newsTypes));
    }

    //新增News对象
    @SystemControllerLog(description = "创建新闻")
    @RequiresPermissions("news:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void createNews(News news, Long newsTypeId, HttpServletResponse response) {
        try {
            news.setCreateTime(new Date());
            NewsType newsType = newsTypeService.getById(newsTypeId);
            news.setNewsType(newsType);
            newsService.update(news);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    //修改News对象
    @SystemControllerLog(description = "修改新闻")
    @RequiresPermissions("news:update")
    @RequestMapping(value = "/update")
    @ResponseBody
    public void updateNews(News news, Long newsTypeId, HttpServletResponse response) {
        try {
            News news2=newsService.getById(news.getId());
            //BeanUtils.copyProperties(news,news2);
            news2.setNewsTitle(news.getNewsTitle());
            news2.setNewsDesc(news.getNewsDesc());
            news2.setNewsContent(news.getNewsContent());
            news2.setNewsImg(news.getNewsImg());
            news2.setSort(news.getSort());
            news2.setUpdateTime(new Date());
          /*  if (news.isShow()) {
                news2.setIsShow(0l);
            } else {
                news2.setIsShow(news.getIsShow());
            }*/
            news2.setSource(news.getSource());

            NewsType newsType = newsTypeService.getById(newsTypeId);
            news2.setNewsType(newsType);

            newsService.update(news2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


    @SystemControllerLog(description = "删除新闻")
    @RequiresPermissions("news:delete")
    @RequestMapping("/delete")
    public void deleteNews(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            Integer count = bannerService.findCountByNewsIds(arrayId);
            if (count > 0) {
                WebUtil.print(response, new Result(false).msg("请先删除对应轮播图!"));
                return;
            }
            newsService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequiresPermissions("news:view")
    @RequestMapping("/findNewsType")
    @ResponseBody
    public Object findNewsType(Long id) {
        News news=newsService.getById(id);
        NewsType newsType=newsTypeService.getById(news.getNewsType().getId());
        //if(null!=newsType){
        return newsType;
        //}else {
        //return null;
        //}
    }
}
