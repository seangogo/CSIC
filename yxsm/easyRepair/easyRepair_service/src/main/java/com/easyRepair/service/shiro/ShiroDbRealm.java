/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.easyRepair.service.shiro;

import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.Resource;
import com.google.common.base.Objects;
import common.utils.Encodes;
import common.utils.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

    // 本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
    protected ShiroServiceImpl shiroService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Manager manager = shiroService.findByLoginName(token.getUsername());
        ServletRequest request = ((WebSubject) SecurityUtils.getSubject()).getServletRequest();
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        httpSession.removeAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (manager!= null) {
            if (manager.isCut()) {
                httpSession.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "您的账户已锁定，请联系系统管理员");
                return null;
            }
            ShiroUser shiroUser = new ShiroUser(manager.getId(), manager.getMobile(),
                    manager.getNickName(), manager.getUserIco());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(shiroUser,
                    manager.getPassword(), ByteSource.Util.bytes(Encodes.decodeHex(manager.getSalt())), getName());
            httpSession.setAttribute("currentUser", manager);
            List<Resource> roots = TreeUtils.builderTree(manager.getRole().getResouces());
            httpSession.setAttribute("userMenu", JsonUtil.obj2Json(roots,"roles"));
            return authenticationInfo;
        } else {
            httpSession.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, "用户不存在");
            return null;
        }
    }

    /**
     * 获取授权信息 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        Manager manager = shiroService.findByLoginName(shiroUser.loginName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = shiroService.findPermissions(manager);
        String roleName = shiroService.findRoles(manager);
        authorizationInfo.addStringPermissions(permissions);
        authorizationInfo.addRole(roleName);
        return authorizationInfo;
    }

    /**
     * 设定Password校验的Hash算法与迭代次数.
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }

    public void setShiroService(ShiroServiceImpl shiroService) {
        this.shiroService = shiroService;
    }

    /**
     * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
     */
    public static class ShiroUser implements Serializable {
        private static final long serialVersionUID = -1373760761780840081L;
        public Long id;
        public String loginName;
        public String name;
        public String userIco;

        public ShiroUser(Long id, String loginName, String name, String userIco) {
            this.id = id;
            this.loginName = loginName;
            this.name = name;
            this.userIco = userIco;
        }

        public String getName() {
            return name;
        }

        public String getUserIco() {
            return userIco;
        }

        /**
         * 本函数输出将作为默认的<shiro:principal/>输出.
         */
        @Override
        public String toString() {
            return loginName;
        }

        /**
         * 重载hashCode,只计算loginName;
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(loginName);
        }

        /**
         * 重载equals,只计算loginName;
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ShiroUser other = (ShiroUser) obj;
            if (loginName == null) {
                if (other.loginName != null) {
                    return false;
                }
            } else if (!loginName.equals(other.loginName)) {
                return false;
            }
            return true;
        }
    }
}
