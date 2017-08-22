package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.TradeInfoService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.TradeInfo;
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
@RequestMapping("/tradeInfo")
public class TradeInfoController {
    @Autowired
    private TradeInfoService tradeInfoService;

    @RequiresPermissions("tradeInfo:view")
    @RequestMapping("/list")
    public String list() {
        return "tradeInfo/list";
    }

    @SystemControllerLog(description = "查看第三方列表")
    @RequiresPermissions("tradeInfo:view")
    @RequestMapping("/page")
    public void newsTypePage(HttpServletResponse response, Integer draw, Integer start, Integer length) {
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Page<TradeInfo> page = tradeInfoService.page(new PageRequest(pageNum - 1, length, Sort.Direction.ASC, "id"));
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
