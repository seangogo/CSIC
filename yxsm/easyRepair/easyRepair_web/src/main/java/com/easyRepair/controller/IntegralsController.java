package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.IntegralsService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Integrals;
import common.core.bean.PageParam;
import common.core.bean.Result;
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

;

/**
 * Created by WangJingYu on 2017/2/15.
 */
@Controller
@RequestMapping("/integrals")
public class IntegralsController {
    @Autowired
    private IntegralsService integralsService;


    @RequestMapping(value = "/list")
    public String list() {
        return "integrals/list";
    }

    @RequestMapping(value = "/integralsPage")
    public void integralsPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Page<Integrals> page = integralsService.page(new PageRequest(pageNum - 1, length, Sort.Direction.ASC, "id"));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@RequiresPermissions("")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public void saveServiceArea(Integrals integrals, HttpServletResponse response){
        try {
            //integralsService.createOrUpdate(integrals);
            if(null==integrals.getId()){
                integrals.setCreateTime(new Date());
            }
            integralsService.update(integrals);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /*@RequestMapping("/deleteServiceArea")
    public void deleteServiceArea(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            integralsService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!请先删除其他关联数据"));
        }
    }*/

}
