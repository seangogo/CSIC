package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.FeedbackService;
import com.easyRepair.service.UserService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Feedback;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by WJY on 2017/2/16.
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;


    @RequiresPermissions("feedback:view")
    @RequestMapping("/list")
    public String list(){
        return "feedback/list";
    }

    @SystemControllerLog(description = "查看意见反馈列表")
    @RequiresPermissions("feedback:view")
    @RequestMapping("/page")
    public void typePage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<Feedback> page = feedbackService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresPermissions("feedback:delete")
    @RequestMapping("/delete")
    public void deleteFeedback(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            feedbackService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

}
