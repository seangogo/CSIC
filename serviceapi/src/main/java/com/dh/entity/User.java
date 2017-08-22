/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_user")
public class User extends IdEntity implements Serializable {
    @JsonIgnore
    private String loginName;//登录名 手机
    private String userName; //用户姓名
    @JsonIgnore
    private String password; //加密的密码
    @JsonIgnore
    private String salt; //盐
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonIgnore
    private Date registerDate; //注册时间
    private String userIco; //头像
    private String userSex; //性别
    private String userAddress; //住址
    private String userCompany; //公司
    @JsonIgnore
    private String userMail; //邮箱
    private String lastLocation; //经纬度
    private Date lastLocationTime; //最后次上传位置时间
    @JsonIgnore
    private Date lastLoginTime; //最后次登录时间
    private Long loginCount; //登录次数
    @JsonIgnore
    private String deviceOs; //登录设备系统
    private String deviceId; //设备id
    private Integer isLogin; //是否登录状态
    private Integer userState; // 用户认证状态 0未认证  1已认证
    private Integer userType; //用户类型 0普通用户 1工程师 2经理
    private Integer repairType;// 工程师类型 0设备维修 1安装实施
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role; //角色
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group; //部门
    private Integer isLocked; //是否锁定状态
    @Transient
    private long currentOrderCount; //工程师当前接单数(?)
    @Transient
    private long totalOrderCount; //工程师总接单数()?
    @Transient
    private String plainPassword; //明文密码
    @Transient
    private String typeStr;//工程师类型对应字典的VALUE 0设备维修 1安装实施

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getUserIco() {
        return userIco;
    }

    public void setUserIco(String userIco) {
        this.userIco = userIco;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Date getLastLocationTime() {
        return lastLocationTime;
    }

    public void setLastLocationTime(Date lastLocationTime) {
        this.lastLocationTime = lastLocationTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public String getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer isLogin) {
        this.isLogin = isLogin;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getRepairType() {
        return repairType;
    }

    public void setRepairType(Integer repairType) {
        this.repairType = repairType;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Integer isLocked) {
        this.isLocked = isLocked;
    }

    public long getCurrentOrderCount() {
        return currentOrderCount;
    }

    public void setCurrentOrderCount(long currentOrderCount) {
        this.currentOrderCount = currentOrderCount;
    }

    public long getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(long totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

}