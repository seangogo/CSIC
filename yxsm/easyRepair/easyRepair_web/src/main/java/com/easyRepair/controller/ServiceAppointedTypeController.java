package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.ServiceAppointedTypeService;
import com.easyRepair.service.ServiceAreaService;
import com.easyRepair.service.ServiceTypeService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.ServiceAppointedType;
import com.easyRepair.tabEntity.ServiceType;
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
 * Created by Administrator on 2017/2/8.
 */
@Controller
@RequestMapping("/serviceAppointedType")
public class ServiceAppointedTypeController {
    @Autowired
    private ServiceAreaService serviceAreaService;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private ServiceAppointedTypeService serviceAppointedTypeService;


    @RequiresPermissions("serviceAppointedType:view")
    @RequestMapping("/list")
    public String toServiceAppointedType( ){
        return "/serviceAppointedType/list";
    }


    @SystemControllerLog(description = "查看订单类别管理列表")
    @RequiresPermissions("serviceAppointedType:view")
    @RequestMapping(value = "/page")
    public void appointedTypePage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<ServiceAppointedType> page = serviceAppointedTypeService.page(searchParams, new PageRequest(pageNum - 1, length,
                    (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "创建订单类别")
    @RequiresPermissions("serviceAppointedType:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void create(ServiceAppointedType serviceAppointedType, HttpServletResponse response) {
        try {
            serviceAppointedType.setCreateTime(new Date());
            serviceAppointedType.setUpdateTime(new Date());
            serviceAppointedTypeService.update(serviceAppointedType);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改订单类别")
    @RequiresPermissions("serviceAppointedType:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(ServiceAppointedType serviceAppointedType, HttpServletResponse response) {
        try {
            serviceAppointedType.setUpdateTime(new Date());
            serviceAppointedTypeService.update(serviceAppointedType);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除订单类别")
    @RequiresPermissions("serviceAppointedType:delete")
    @RequestMapping("/delete")
    public void deleteServiceAppointedType(HttpServletResponse response,String ids){
        StringBuffer stringBuffer=new StringBuffer();
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            List<ServiceType> serviceTypes=serviceTypeService.findByAppointedIds(arrayId);
            for(ServiceType serviceType:serviceTypes){
                stringBuffer.append(serviceType.getServiceName()).append(";");
            }
            if(serviceTypes.size()>0){
                WebUtil.print(response, new Result(false).msg("删除失败!请先删除其他关联数据:"+stringBuffer));
            }else {
                serviceAppointedTypeService.deleteAll(arrayId);
                WebUtil.print(response, new Result(true).msg("删除成功!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!系统出错"));
        }
    }


}
