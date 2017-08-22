package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.NewsTypeService;
import com.easyRepair.service.commons.DataTableFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
@Controller
@RequestMapping("/newsType")
public class NewsTypeController {
    @Autowired
    private NewsTypeService newsTypeService;

    @RequiresPermissions("newsType:view")
    @RequestMapping("/list")
    public String newsType(){
        return "newsType/list";
    }

    @SystemControllerLog(description = "查看新闻分类列表")
    @RequiresPermissions("newsType:view")
    @RequestMapping(value = "/page")
    public void newsTypePage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<NewsType> page = newsTypeService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "创建新闻分类")
    @RequiresPermissions("newsType:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void create(NewsType newsType, HttpServletResponse response) {
        try {
            newsTypeService.update(newsType);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改新闻分类")
    @RequiresPermissions("newsType:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(NewsType newsType, HttpServletResponse response) {
        try {
            newsTypeService.update(newsType);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


    @SystemControllerLog(description = "删除新闻分类")
    @RequiresPermissions("newsType:delete")
    @RequestMapping("/delete")
    public void deleteNewsType(HttpServletResponse response,String ids){
        try {

            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            newsTypeService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!请先删除其他关联数据"));
        }
    }
}
