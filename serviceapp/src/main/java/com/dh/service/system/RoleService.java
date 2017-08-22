package com.dh.service.system;

import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.Role;
import com.dh.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.Clock;

import java.util.List;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    private Clock clock = Clock.DEFAULT;

//	public void setRoleDao(RoleDao roleDao) {
//		this.roleDao = roleDao;
//	}

    public List<Role> findRoles() {
        return roleDao.findRoles();
    }

    @SystemServiceLog(description = "查询角色列表")
    public List<Role> findAll() {
        return (List<Role>) roleDao.findAll();
    }

    public Role findRoleById(Long roleId) {
        return roleDao.findOne(roleId);
    }

    public void saveRole(Role role) {
        role.setUpdateTime(clock.getCurrentDate());
        roleDao.save(role);
    }

    public void deleteRole(Long roleId) {
        /*先删除关联关系*/
        Role role = roleDao.findOne(roleId);
        role.getResources().clear();

        roleDao.delete(roleId);
    }

    public Role findByRoleName(String roleName) {
        return roleDao.findByRoleName(roleName);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
