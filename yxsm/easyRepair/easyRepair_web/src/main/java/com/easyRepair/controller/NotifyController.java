package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.NotifyService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Notify;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/28.
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    @RequiresPermissions("notify:view")
    @RequestMapping("/list")
    public String list() {
        return "notify/list";
    }

    @SystemControllerLog(description = "查看支付宝列表")
    @RequiresPermissions("notify:view")
    @RequestMapping("/page")
    public void newsTypePage(HttpServletResponse response, Integer draw, Integer start, Integer length) {
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Page<Notify> page = notifyService.page(new PageRequest(pageNum - 1, length, Sort.Direction.ASC, "id"));
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
