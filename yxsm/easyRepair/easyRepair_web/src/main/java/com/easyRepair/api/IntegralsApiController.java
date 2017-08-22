package com.easyRepair.api;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.entityUtils.SessionUtils;
import com.easyRepair.pojo.UserSessionModul;
import com.easyRepair.service.IntegralsService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Integrals;
import common.core.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 Created by sean on 2016/12/1. */
@Api(value = "/integrals", description = "积分相关接口")
@Controller
@RequestMapping(value = {"/api/integrals"}, produces = {"text/html;charset=UTF-8"})
public class IntegralsApiController {
    @Autowired
    private IntegralsService integralsService;
    
    /**
     @param
     @return
     @Description 查询用户积分变动记录列表
     @date 2016-12-01
     @author sean
     */
    @RequestMapping(value = "{pageNum}/integrals")
    @ResponseBody
    @ApiOperation(notes = "我的积分记录分页,", httpMethod = "POST", value = "积分记录")
    public void page(@ApiParam(name = "pageNum", value = "页码")
                     @PathVariable(value = "pageNum") Integer pageNum, HttpServletResponse response) {
        Page<Integrals> integralsPage = integralsService.findByUserId(((UserSessionModul) SessionUtils.getCurrentUser()).getId(), new PageRequest(pageNum - 1,10));
        WebUtil.printApi(response, new Result(true).data(DataTableFactory.fitting(integralsPage)));
    }
    
    /**
     * @Description 分享获得积分
     * @param usersId 用户id
     * @return
     * @date 2016-12-01
     * @author sean
     */
    
}
