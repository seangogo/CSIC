package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.dao.OrderDao;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.*;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.*;
import common.annotation.SystemControllerLog;
import common.core.bean.PageParam;
import common.core.bean.Result;
import common.utils.JsonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.cfg.ResultSetMappingBinder;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WJY on 2017/2/20.
 */
@Controller
@RequestMapping("/serviceType")
public class ServiceTypeController {
    @Autowired
    private ServiceTypeService serviceTypeService;

    @Autowired
    private ServiceAreaService serviceAreaService;

    @Autowired
    private ServiceAppointedTypeService serviceAppointedTypeService;

    @Autowired
    private ServiceTypeAreaService serviceTypeAreaService;
    @Autowired
    private OrderService orderService;

    @RequiresPermissions("serviceType:view")
    @RequestMapping("/list")
    public String toServiceType( ){
        return "/serviceType/list";
    }

    @SystemControllerLog(description = "查看订单服务类别列表")
    @RequiresPermissions("serviceType:view")
    @RequestMapping(value = "/page")
    public void typePage(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Page<ServiceType> page = serviceTypeService.page(searchParams, new PageRequest(pageNum - 1, length,
                    (Sort.Direction) sort[1], sort[0].toString()));
            Map<String, Object> result = DataTableFactory.fitting(draw,page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "新增订单服务类别管理")
    @RequiresPermissions("serviceType:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void createServiceType(ServiceType serviceType, Long serviceAppointedTypeId, HttpServletResponse response) {
        try {
            //serviceAppointedTypeService.getById(serviceAppointedTypeId);
            ServiceAppointedType serviceAppointedType = new ServiceAppointedType();
            serviceAppointedType.setId(serviceAppointedTypeId);
            serviceType.setServiceAppointedType(serviceAppointedType);
            serviceType.setCreateTime(new Date());
            serviceType.setUpdateTime(new Date());
            serviceTypeService.update(serviceType);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改订单服务类别管理")
    @RequiresPermissions("serviceType:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateServiceType(ServiceType serviceType, Long serviceTypeId, Long serviceAppointedTypeId, String serviceAreaIds, HttpServletResponse response) {
        try {
            if (serviceAreaIds != null) {
                long[] arrayId = JsonUtil.json2Obj(serviceAreaIds, long[].class);
                for (long l : arrayId) {
                    if (serviceTypeAreaService.findCountByServiceTypeIdAndServiceAreaId(serviceTypeId, l) == 0) {
                        ServiceTypeArea serviceTypeArea = new ServiceTypeArea();
                        serviceTypeArea.setServiceAreaId(l);
                        serviceTypeArea.setServiceTypeId(serviceTypeId);
                        serviceTypeAreaService.update(serviceTypeArea);
                    }
                }
            } else {
                ServiceType serviceType1 = serviceTypeService.getById(serviceType.getId());
                serviceType1.setUpdateTime(new Date());
                serviceType1.setExpenses(serviceType.getExpenses());
                serviceType1.setPrice(serviceType.getPrice());
                serviceType1.setServiceName(serviceType.getServiceName());
                ServiceAppointedType serviceAppointedType = serviceAppointedTypeService.getById(serviceAppointedTypeId);
                serviceType1.setServiceAppointedType(serviceAppointedType);
                serviceTypeService.update(serviceType1);
            }
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "删除订单服务类别管理")
    @RequiresPermissions("serviceType:delete")
    @RequestMapping("/delete")
    public void deleteServiceType(HttpServletResponse response, String ids) {
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            List<Order> orders = orderService.findByServiceTypeIds(arrayId);
            if (orders.size() > 0) {
                WebUtil.print(response, new Result(false).msg("操作失败!需要先删除订单级联数据"));
                return;
            }
            serviceTypeAreaService.deleteByServiceTypeIds(arrayId);
            serviceTypeService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("删除成功"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequiresPermissions("serviceType:view")
    @RequestMapping("/findServiceArea")
    @ResponseBody
    public List<ServiceArea> findServiceArea(Long id) {
        List<ServiceArea> serviceAreas = serviceAreaService.findByTypeId(id);
        return serviceAreas;
    }

    @RequiresPermissions("serviceType:view")
    @RequestMapping("/allServiceArea")
    @ResponseBody
    public List<TreeNode> allServiceArea() {
        List<TreeNode> serviceAreas = serviceAreaService.findServiceAreaTree();
        return serviceAreas;
    }

    @RequiresPermissions("serviceType:view")
    @RequestMapping("/allServiceAppointedType")
    @ResponseBody
    public List<ServiceAppointedType> allServiceAppointedType() {
        List<ServiceAppointedType> serviceAppointedTypes = serviceAppointedTypeService.findAll();
        return serviceAppointedTypes;
    }

    @RequiresPermissions("serviceType:view")
    @RequestMapping("/findServiceAppointedType")
    @ResponseBody
    public Object findServiceAppointedType(Long id) {
        ServiceType serviceType = serviceTypeService.getById(id);
        return serviceType.getServiceAppointedType();
    }
}
