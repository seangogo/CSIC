package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.CommentsService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Banner;
import com.easyRepair.tabEntity.Comments;
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
import java.util.Map;

/**
 * Created by WJY on 2017/3/21.
 */
@Controller
@RequestMapping("/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;


    @RequiresPermissions("comments:view")
    @RequestMapping("/list")
    public String list() {
        return "comments/list";
    }

    @SystemControllerLog(description = "查看评论列表")
    @RequiresPermissions("comments:view")
    @RequestMapping(value = "/page")
    public void page(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<Comments> page = commentsService.page(searchParams, new PageRequest(pageNum - 1, length,
                    (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //新增Banner对象
    @SystemControllerLog(description = "创建评论")
    @RequiresPermissions("comments:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void create(Comments comments, Long orderId, HttpServletResponse response) {
        try {

            comments.setCreateTime(new Date());
            commentsService.update(comments);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改评论")
    @RequiresPermissions("comments:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(Comments comments, Long orderId, HttpServletResponse response) {
        try {
            Comments comments2 = commentsService.getById(comments.getId());

            commentsService.update(comments2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除评论")
    @RequiresPermissions("comments:delete")
    @RequestMapping("/delete")
    public void delete(HttpServletResponse response, String ids) {
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            commentsService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

}
