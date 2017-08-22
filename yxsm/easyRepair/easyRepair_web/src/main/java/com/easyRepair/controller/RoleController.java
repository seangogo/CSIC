package com.easyRepair.controller;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.ResourceService;
import com.easyRepair.service.RoleService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Resource;
import com.easyRepair.tabEntity.Role;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/24.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("list")
    public String list() {
        return "role/list";
    }

    /**
     * 角色模块分页
     * @param request
     * @param start
     * @param length
     */
    @RequiresPermissions("role:view")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> page(HttpServletRequest request, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            Object[] sort = WebUtil.getSortParameters(request);
            int pageNum = PageParam.getPageNum(start, length);
            Page<Role> page = roleService.page(searchParams,new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()));
            return DataTableFactory.dataTableFitting(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 角色新增修改
     * @param role 前台角色模型
     * @param ids  关联的菜单ID
     * @param response
     */
    @RequiresPermissions("role:create")
    @SystemControllerLog(description = "新增角色")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public void save(@ModelAttribute("role") Role role, @RequestParam String ids,
                     HttpServletResponse response) {
        try {
            roleService.createAndUpdate(role,ids);
            WebUtil.print(response, new Result(true).msg("操作成功"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败"));
            e.printStackTrace();
        }
    }

    /**
     * 根据角色ID得到所有菜单ID
     * @param roleId
     * @return
     */
    @RequiresPermissions("role:view")
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    @ResponseBody
    public List<Resource> getRoleResources(@RequestParam(value = "roleId") Long roleId) {
        return resourceService.findRoleResourcesByRoleId(roleId);
    }

    /**
     * 前端树形图原型
     * @return
     */
    @RequiresPermissions("role:view")
    @RequestMapping(value = "allResource", method = RequestMethod.GET)
    @ResponseBody
    public List<TreeNode> getResources() {
        return resourceService.findRoleResources();
    }

    /**
     * 删除角色和关联记录，关联后台管理员不可删除
     * @param response
     */
    @RequiresPermissions("role:delete")
    @SystemControllerLog(description = "删除角色")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ResponseBody
    public void delete(HttpServletResponse response, String ids) {
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            WebUtil.print(response, roleService.delete(arrayId));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败"));
        }
    }

}
