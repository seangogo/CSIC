package com.easyRepair.api;

import com.easyRepair.service.ServiceAppointedTypeService;
import com.easyRepair.service.ServiceAreaService;
import com.easyRepair.service.ServiceTypeService;
import com.easyRepair.tabEntity.ServiceAppointedType;
import com.easyRepair.tabEntity.ServiceArea;
import com.easyRepair.tabEntity.ServiceType;
import common.core.bean.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 Created by sean on 2016/11/22. */
@Api(value = "/service", description = "关于工程师服务类型/用户服务类型相关接口")
@RestController
@RequestMapping(value = "/api/service")
public class ServiceApiController {
    @Autowired
    private ServiceAreaService serviceAreaService;
    
    @Autowired
    private ServiceTypeService serviceTypeService;
    
    @Autowired
    private ServiceAppointedTypeService serviceAppointedTypeService;
    
    /**
     查询所有服务领域
     */
    @RequestMapping(value = "/allServiceArea")
    @ApiOperation(notes = "(工程师认证页面)查询所有服务领域,可以多选,如果是工程师认证必传一个", httpMethod = "GET", value = "服务领域")
    public Result allServiceArea(HttpServletResponse response) {
        List<ServiceArea> serviceAreas = serviceAreaService.findAll();
       // ObjectMapper objectMapper = new ObjectMapper();
        try {
          /*  List<Map<String, String>> serviceAreasList = JsonUtil.json2Obj(objectMapper.writeValueAsString(serviceAreas), new TypeToken<List<Map<String, String>>>() {
            }.getType());*/
           return new Result(true).data(serviceAreas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     查询所有服务领域
     */
    @RequestMapping(value = "/allServiceAppoint")
    @ApiOperation(notes = "查询所有服务类型", httpMethod = "GET", value = "一级分类")
    public Result allServiceAppoint() {
        List<ServiceAppointedType> serviceAppointedTypes = serviceAppointedTypeService.findAll();
        return new Result(true).data(serviceAppointedTypes);
    }
    
    
    /**
     查询指定设备类型下的所有服务类型和价格
     */
    @RequestMapping(value = "/ServiceType/{appointedId}")
    @ApiOperation(notes = "用于发布订单页面的服务类型选择，(用户发布订单页面)查询指定设备所有服务类型", httpMethod = "GET", value = "服务类型")
    public Result allServiceType(@ApiParam(name = "appointedId", value = "用于查询服务类型的设备ID")
                               @PathVariable(value = "appointedId") Long appointedId) {
        List<ServiceType> types = serviceTypeService.findByServiceAppointedType_id(appointedId);
        return new Result(true).msg("获取成功").data(types);
    }
}
