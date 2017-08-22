/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "t_user")
public class User extends IdEntity {
	private String loginName;// 登录名 手机
	private String userName; // 用户姓名
	private String plainPassword; // 明文密码
	private String password; // 加密的密码
	private String salt; // 盐
	private Role role; // 角色
	private Date registerDate; // 注册时间
	private String userIco; // 头像
	private String userSex; // 性别
	private String userAddress; // 住址
	private String userCompany; // 公司
	private String userMail; // 邮箱
	private String lastLocation; // 经纬度
	private Date lastLoginTime; // 最后次登录时间
	private Date lastLocationTime; // 最后次上传位置时间
	private Long loginCount; // 登录次数
	private String deviceOs; // 登录设备系统
	private String deviceId; // 设备id
	private Integer isLogin; // 是否登录状态
	private Integer userState; // 用户认证状态 0未认证 1已认证
	private Integer userType; // 用户类型 0普通用户 1工程师 2经理
	private Integer repairType;// 工程师类型 0设备维修 1安装实施
	// private Group group; //部门
	private Integer isLocked; // 是否锁定状态

	private Long currentOrderCount; // 工程师当前接单数
	private Long totalOrderCount; // 工程师总接单数

	public User(Long id, String loginName, String userName, String userIco, String lastLocation, Integer repairType,
			Long totalOrderCount, Long currentOrderCount) {
		this.id = id;
		this.loginName = loginName;
		this.userName = userName;
		this.userIco = userIco;
		this.lastLocation = lastLocation;
		this.repairType = repairType;
		this.currentOrderCount = currentOrderCount;
		this.totalOrderCount = totalOrderCount;
	}
    public User(Long id,String loginName, String userName, String userIco, String lastLocation, Integer repairType,Integer isLogin,Date lastLocationTime,
                Long totalOrderCount, Long currentOrderCount) {
        this.id = id;
        this.loginName = loginName;
        this.userName = userName;
        this.userIco = userIco;
        this.lastLocation = lastLocation;
        this.repairType = repairType;
        this.isLogin = isLogin;
        this.currentOrderCount = currentOrderCount;
        this.totalOrderCount = totalOrderCount;
        this.lastLocationTime = lastLocationTime;
    }

	public User(Long id,String userName) {
		this.id = id;
		this.userName = userName;
	}

	@Transient
	public Long getCurrentOrderCount() {
		return currentOrderCount;
	}

	public void setCurrentOrderCount(Long currentOrderCount) {
		this.currentOrderCount = currentOrderCount;
	}

	@Transient
	public Long getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(Long totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	@NotBlank
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

	// 不持久化到数据库，也不显示在Restful接口的属性.
	@Transient
	@JsonIgnore
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
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

	// JPA 基于role_id列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Transient
	public Long getRoleId() {
		if (role == null) {
			return null;
		}
		return role.getId();
	}

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getLastLocationTime() {
		return lastLocationTime;
	}

	public void setLastLocationTime(Date lastLocationTime) {
		this.lastLocationTime = lastLocationTime;
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

	// 设定JSON序列化时的日期格式
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	// // JPA 基于role_id列的多对一关系定义
	// @ManyToOne
	// @JoinColumn(name = "group_id")
	// public Group getGroup() {
	// return group;
	// }
	//
	// public void setGroup(Group group) {
	// this.group = group;
	// }
	//
	// @Transient
	// public Long getGroupId() {
	// return group.getId();
	// }

	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}

	@Override
	public String toString() {
		return "{ \"userId\": " + id + ",\"loginName\":\"" + loginName + "\",\"userName\":\"" + userName
				+ "\",\"registerDate\":\"" + registerDate + "\",\"userIco\":\"" + userIco + "\",\"userSex\":\""
				+ userSex + "\",\"userAddress\":\"" + userAddress + "\",\"userCompany\":\"" + userCompany
				+ "\",\"userMail\":\"" + userMail + "\",\"lastLocation\":\"" + lastLocation + "\",\"lastLoginTime\":\""
				+ lastLoginTime + "\",\"loginCount\":\"" + loginCount + "\",\"deviceOs\":\"" + deviceOs
				+ "\",\"deviceId\":\"" + deviceId + "\",\"isLogin\":\"" + isLogin + "\",\"userState\":\"" + userState
				+ "\",\"userType\":\"" + userType + "\",\"repairType\":\"" + repairType + "\",\"isLocked\":\""
				+ isLocked + "\",\"lastLocationTime\":\"" + lastLocationTime + "\"}";
	}

	// @Override
	// public String toString() {
	// return ToStringBuilder.reflectionToString(this);
	// }

}