package com.easyRepair.service.shiro;

import com.easyRepair.service.ManagerService;
import com.easyRepair.service.ResourceService;
import com.easyRepair.service.UserService;
import com.easyRepair.tabEntity.Manager;
import common.annotation.SystemServiceLog;
import common.utils.Digests;
import common.utils.Encodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 Created by sean on 2016/12/23. */
@Component
@Transactional
public class ShiroServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(ManagerService.class);
    @Autowired
    private ManagerService managerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;


    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(Manager manager) {
        byte[] salt = Digests.generateSalt(8);
        manager.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(manager.getPassword().getBytes(), salt, 1024);
        manager.setPassword(Encodes.encodeHex(hashPassword));
    }


    public boolean check(String loginName, String password) {
        return userService.checkUserPassword(managerService.findByLoginName(loginName).getUser(), password);
    }
    
     @SystemServiceLog(description = "根据用户名查询用户")
    public Manager findByLoginName(String loginName) {
        return managerService.findByLoginName(loginName);
    }

    
     @SystemServiceLog(description = "查询用户的角色")
    public String findRoles(Manager manager) {
        if(manager == null || null == manager.getRole()) {
            return "";
        }
        return manager.getRole().getName();
    }
    
    @SystemServiceLog(description = "查询用户的权限")
    public List<String> findPermissions(Manager manager) {
        if(manager == null || null == manager.getRole()) {
            return new ArrayList<String>();
        }
        return resourceService.findPermissions(manager.getRole().getResouces());
    }
    
}
