package com.easyRepair.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyRepair.service.RoleService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.tabEntity.Role;
import common.annotation.SystemControllerLog;
import common.core.Constant;
import common.utils.JsonUtil;
import common.utils.password.PasswordUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyRepair.commons.WebUtil;
import com.easyRepair.service.ManagerService;
import com.easyRepair.service.UserService;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.User;

import common.core.bean.PageParam;
import common.core.bean.Result;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 Created by sean on 2016/11/14. */
@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    //@ResponseBody
    //@SystemControllerLog(description = "进入后台用户界面")
    @RequiresPermissions("manager:view")
    @RequestMapping("/list")
    public String list() {
        return "manager/list";
    }
    
    @RequestMapping("profile")
    public String profile() {
        return "manager/profile";
    }

    @RequestMapping("main")
    public String main() {
        return "inc/main";
    }

    @SystemControllerLog(description = "创建后台用户")
    @RequiresPermissions("manager:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public void create(Manager manager, Long roleId, HttpServletResponse response) {
        try {
            Role role = roleService.getById(roleId);
            manager.setRole(role);
            manager.setCreateTime(new Date());

            //生成散列密码和盐
            /*String salt = UUID.randomUUID().toString();
            SimpleHash simpleHash = new SimpleHash("SHA-1", manager.getPassword(), salt, 1024);
            String password = simpleHash.toString();
            manager.setSalt(salt);
            manager.setPassword(password);*/
            String[] passwords = PasswordUtil.entryptSaltAndPassword(manager.getPassword());
            manager.setPassword(passwords[1]);
            manager.setSalt(passwords[0]);
            managerService.update(manager);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @SystemControllerLog(description = "修改后台用户")
    @RequiresPermissions("manager:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(Manager manager, Long roleId, HttpServletResponse response) {
        try {
            Manager manager2 = managerService.getById(manager.getId());
            Role role = roleService.getById(roleId);
            manager2.setRole(role);
            manager2.setNickName(manager.getNickName());
            manager2.setMobile(manager.getMobile());
            manager2.setUserIco(manager.getUserIco());

            managerService.update(manager2);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


    @SystemControllerLog(description = "查看后台用户列表")
    @RequiresPermissions("manager:view")
    @RequestMapping(value = "/page")
    public void page(HttpServletRequest request, HttpServletResponse response, Integer draw, Integer start, Integer length) {
        Map<String, Object> searchParams = WebUtil.getParametersStartingWith(request, "search_");
        try {
            int pageNum = PageParam.getPageNum(start, length);
            Object[] sort = WebUtil.getSortParameters(request);
            Map<String, Object> result = managerService.pageResult(searchParams, new PageRequest(pageNum - 1, length, (Sort.Direction) sort[1], sort[0].toString()), draw);
            WebUtil.printJson(response, JsonUtil.obj2Json(result, "role", "user"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "删除后台用户")
    @RequiresPermissions("manager:delete")
    @RequestMapping(value = "/delete")
    public void delete(HttpServletResponse response, String ids) {
        try {
            long[] arrayId = JsonUtil.json2Obj(ids, long[].class);
            List<Role> roles = managerService.findRolesByIds(arrayId);
            for (Role role : roles) {
                if (role.getId() == 1l) {
                    WebUtil.print(response, new Result(false).msg("无法删除超级管理员!"));
                    return;
                }
            }
            managerService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("删除成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("删除失败!"));
        }
    }

    @RequiresPermissions("manager:view")
    @RequestMapping("/checkMobile")
    public void checkMobile(HttpServletResponse response, String mobile) {
        Map<String, Object> map = new HashedMap();
        Manager manager = managerService.findByLoginName(mobile);
        if (manager != null) {
            map.put("error", "该账号已经注册!");
            WebUtil.print(response, new Result(false).data(map));
        }
    }

    @RequiresPermissions("manager:view")
    @RequestMapping("/checkNickName")
    public void checkNickName(HttpServletResponse response, String nickName, Long id) {
        if (id != null && !id.toString().trim().equals("")) {
            Manager manager = managerService.getById(id);
            if (manager.getNickName().equals(nickName)) {
                return;
            }
        }
        Integer count = managerService.findCountByNickName(nickName);
        if (count > 0) {
            WebUtil.print(response, new Result(false).msg("该昵称已被使用!"));
        }
    }

    @RequiresPermissions("manager:view")
    @RequestMapping(value = "/allRole")
    @ResponseBody
    public List<Role> allRole() {
        try {
            List<Role> roles = roleService.findAll();
            return roles;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequiresPermissions("manager:view")
    @RequestMapping(value = "/findRole")
    @ResponseBody
    public Map<String, Object> findRole(Long id) {
        try {
            Map<String, Object> map = new HashedMap();
            Role role = managerService.getRoleById(id);
            Long roleId = role.getId();
            map.put("roleId", roleId);
            map.put("roleName", role.getName());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
