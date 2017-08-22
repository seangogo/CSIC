package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.ServiceAreaService;
import com.easyRepair.service.ServiceTypeAreaService;
import com.easyRepair.service.ServiceTypeService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.ServiceArea;
import com.easyRepair.tabEntity.ServiceType;
import com.easyRepair.tabEntity.ServiceTypeArea;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/21.
 */
@Controller
@RequestMapping("/serviceTypeArea")
public class ServiceTypeAreaController {
    @Autowired
    private ServiceTypeAreaService serviceTypeAreaService;

    @Autowired
    private ServiceAreaService serviceAreaService;

    @Autowired
    private ServiceTypeService serviceTypeService;

    @RequiresPermissions("serviceTypeArea:view")
    @RequestMapping("/list")
    public String list() {
        return "serviceTypeArea/list";
    }

    @SystemControllerLog(description = "查看工程师和服务类型关联列表")
    @RequiresPermissions("serviceTypeArea:view")
    @RequestMapping("/page")
    public void page(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<ServiceTypeArea> page = serviceTypeAreaService.page(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //新增ServiceTypeArea对象
    @SystemControllerLog(description = "新增工程师和服务类型关联")
    @RequiresPermissions("serviceTypeArea:creste")
    @RequestMapping(value = "/creste", method = RequestMethod.POST)
    //@ResponseBody
    public void creste(ServiceTypeArea serviceTypeArea, HttpServletResponse response) {
        /*if (serviceAreaService.getById(serviceTypeArea.getServiceAreaId()) == null) {
            WebUtil.print(response, new Result(false).msg("不存在此服务类型!"));
        }
        if (serviceTypeService.getById(serviceTypeArea.getServiceTypeId()) == null) {
            WebUtil.print(response, new Result(false).msg("不存在此工程师类型!"));
        }
        if (serviceTypeAreaService.findByIds(
                serviceTypeArea.getServiceAreaId(), serviceTypeArea.getServiceTypeId()) != null) {
            WebUtil.print(response, new Result(false).msg("已存在此关联关系!"));
        }*/

        serviceTypeAreaService.update(serviceTypeArea);
        WebUtil.print(response, new Result(true).msg("操作成功!"));
    }

    //修改ServiceTypeArea对象
    @SystemControllerLog(description = "修改工程师和服务类型关联")
    @RequiresPermissions("serviceTypeArea:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    //@ResponseBody
    public void update(ServiceTypeArea serviceTypeArea, HttpServletResponse response) {
        serviceTypeAreaService.update(serviceTypeArea);
        WebUtil.print(response, new Result(true).msg("操作成功!"));
    }


    @SystemControllerLog(description = "删除工程师和服务类型关联")
    @RequiresPermissions("serviceTypeArea:delete")
    @RequestMapping("/delete")
    public void delete(HttpServletResponse response, String ids) {
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            serviceTypeAreaService.deleteByIds(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!请先删除其他关联数据"));
        }
    }

    @RequiresPermissions("serviceTypeArea:view")
    @RequestMapping("/allServiceType")
    @ResponseBody
    public List<ServiceType> allServiceType() {
        List<ServiceType> serviceTypes = serviceTypeService.findAll();
        return serviceTypes;
    }

    @RequiresPermissions("serviceTypeArea:view")
    @RequestMapping("/allServiceArea")
    @ResponseBody
    public List<ServiceArea> allServiceArea() {
        List<ServiceArea> serviceAreas = serviceAreaService.findAll();
        return serviceAreas;
    }

    @RequiresPermissions("serviceTypeArea:view")
    @RequestMapping("/findServiceTypeAndServiceArea")
    @ResponseBody
    public Map<String, Object> findServiceType(Long id) {
        ServiceTypeArea serviceTypeArea = serviceTypeAreaService.getById(id);
        Long serviceAreaId = serviceTypeArea.getServiceAreaId();
        Long serviceTypeId = serviceTypeArea.getServiceTypeId();
        Map<String, Object> map = new HashedMap();
        map.put("serviceAreaId", serviceAreaId);
        map.put("serviceTypeId", serviceTypeId);
        return map;
    }
}
