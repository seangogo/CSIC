package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.NotifyService;
import com.easyRepair.service.TradeInfoService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Notify;
import com.easyRepair.tabEntity.TradeInfo;
import common.core.bean.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/17.
 */
@Controller
@RequestMapping("/payCallback")
public class PayCallbackController {

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private TradeInfoService tradeInfoService;

    @RequestMapping("/notify")
    public String notifyList(){
        return "/notify/list";
    }

    @RequestMapping("/tradeInfo")
    public String tradeInfoList(){
        return "/tradeInfo/list";
    }

    @RequestMapping(value = "/notifyPage")
    public void notifyPage(HttpServletResponse response, Integer draw, Integer start, Integer length) {
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Page<Notify> page = notifyService.page(new PageRequest(pageNum - 1,length, Sort.Direction.ASC, "id"));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/tradeInfoPage")
    public void tradeInfoPage(HttpServletResponse response, Integer draw, Integer start, Integer length) {
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Page<TradeInfo> page = tradeInfoService.page(new PageRequest(pageNum - 1,length, Sort.Direction.ASC, "id"));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
