package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.pojo.tree.TreeNode;
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
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/20.
 */
@Controller
@RequestMapping("/serviceArea")
public class ServiceAreaController {
    @Autowired
    private ServiceAreaService serviceAreaService;

    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private ServiceTypeAreaService serviceTypeAreaService;

    @RequiresPermissions("serviceArea:view")
    @RequestMapping("/list")
    public String toServiceArea( ){
        return "/serviceArea/list";
    }


    @RequiresPermissions("serviceArea:view")
    @RequestMapping("/allServiceType")
    @ResponseBody
    public List<TreeNode> allServiceType() {
        List<TreeNode> serviceTypes = serviceTypeService.findServiceTypeTree();
        return serviceTypes;
    }

    @RequiresPermissions("serviceArea:view")
    @RequestMapping("/findServiceType")
    @ResponseBody
    public List<ServiceType> findServiceType(Long id) {
        List<ServiceType> serviceTypes=serviceTypeService.findByAreaId(id);
        return serviceTypes;
    }


    @SystemControllerLog(description = "查看工程师服务领域列表")
    @RequiresPermissions("serviceArea:view")
    @RequestMapping(value = "/page")
    public void areaPage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<ServiceArea> page = serviceAreaService.page(searchParams, new PageRequest(pageNum - 1, length,
                    (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SystemControllerLog(description = "新增工程师服务领域")
    @RequiresPermissions("serviceArea:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void create(ServiceArea serviceArea, HttpServletResponse response) {
        try {
            serviceArea.setCreateTime(new Date());
            serviceArea.setUpdateTime(new Date());
            serviceAreaService.update(serviceArea);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改工程师服务领域")
    @RequiresPermissions("serviceArea:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(ServiceArea serviceArea, Long serviceAreaId, String serviceTypeIds, HttpServletResponse response) {
        try {
            if (serviceTypeIds != null) {
                long[] arrayId = JsonUtil.json2Obj(serviceTypeIds, long[].class);
                for (long l : arrayId) {
                    if (serviceTypeAreaService.findCountByServiceTypeIdAndServiceAreaId(serviceAreaId, l) == 0) {
                        ServiceTypeArea serviceTypeArea = new ServiceTypeArea();
                        serviceTypeArea.setServiceAreaId(serviceAreaId);
                        serviceTypeArea.setServiceTypeId(l);
                        serviceTypeAreaService.update(serviceTypeArea);
                    }
                }
            } else {
                serviceArea.setUpdateTime(new Date());
                serviceAreaService.update(serviceArea);
            }
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除工程师服务领域")
    @RequiresPermissions("serviceArea:delete")
    @RequestMapping("/delete")
    public void deleteServiceArea(HttpServletResponse response,String ids){
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            serviceAreaService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!请先删除其他关联数据"));
        }
    }
}
