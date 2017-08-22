package com.dh.web.system;

import com.dh.configure.annotation.SystemControllerLog;
import com.dh.entity.Resource;
import com.dh.entity.Role;
import com.dh.dto.TreeNode;
import com.dh.entity.User;
import com.dh.service.account.AccountService;
import com.dh.service.system.ResourceService;
import com.dh.service.system.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理Controller
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private AccountService accountService;

    @RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    @SystemControllerLog(description = "查看角色列表")
    public String getRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("index", "system/role");
        model.addAttribute("tableType", resourceService.findTabeType("system/role"));
        return "system/role";
    }

    @RequiresPermissions("role:view")
    @RequestMapping(value = "resource", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getRoleResources(@RequestParam(value = "roleId") Long roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Role role = roleService.findRoleById(roleId);

        Role roleTo = new Role();
        roleTo.setId(role.getId());
        roleTo.setRoleName(role.getRoleName());
        roleTo.setUpdateTime(role.getUpdateTime());
        roleTo.setDescription(role.getDescription());
        List<TreeNode> treeNodes = resourceService.findRoleResources(roleId);
        map.put("roleInfo", roleTo);
        map.put("roleResource", treeNodes);
        return map;
    }

    @RequiresPermissions("role:view")
    @RequestMapping(value = "allResource", method = RequestMethod.POST)
    @ResponseBody
    public List<TreeNode> getResources() {
        return resourceService.findRoleResources();
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @SystemControllerLog(description = "更新角色")
    public String update(HttpServletRequest request, @Valid @ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
        String ids = request.getParameter("resourceIds").toString();
        List<String> idsArray = Arrays.asList(ids.split(","));
        role.getResources().clear();
        for (String str : idsArray) {
            Resource resource = new Resource();
            resource.setId(Long.parseLong(str));
            role.getResources().add(resource);
        }
        roleService.saveRole(role);

        User user = (User) request.getSession().getAttribute("currentUser");
        if (user.getRole().getId() == role.getId()) {
            request.getSession().setAttribute("userMenu", resourceService.findPermissions(user.getRole().getResources()));
        }

        redirectAttributes.addFlashAttribute("message", "修改成功");
        return "redirect:/system/role";
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @SystemControllerLog(description = "新增角色")
    public String create(HttpServletRequest request, @Valid @ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
        String ids = request.getParameter("resourceIds").toString();
        List<String> idsArray = Arrays.asList(ids.split(","));
        for (String str : idsArray) {
            Resource resource = new Resource();
            resource.setId(Long.parseLong(str));
            role.getResources().add(resource);
        }
        roleService.saveRole(role);
        redirectAttributes.addFlashAttribute("message", "新增成功");
        return "redirect:/system/role";
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "delete/{id}")
    @SystemControllerLog(description = "删除角色")
    public String delete(@PathVariable("id") Long roleId, RedirectAttributes redirectAttributes) {
        if (accountService.countByRoldId(roleId) > 0) {
            redirectAttributes.addFlashAttribute("message", "该角色已被使用，请检查！");
        } else {
            roleService.deleteRole(roleId);
            redirectAttributes.addFlashAttribute("message", "删除成功");
        }
        return "redirect:/system/role";
    }
}
