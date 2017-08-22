/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_user_feedback")
public class Feedback extends IdEntity implements Serializable {
    private String deviceVersion;//设备型号
    private String deviceSystem;//设备系统版本
    private String deviceSize;//设备分辨率
    private String deviceNetwork;//设备网络类型
    /**
     * 添加时间
     */
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    private String content;//反馈内容
    private String appVersion;//app的版本
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;//用户

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceSystem() {
        return deviceSystem;
    }

    public void setDeviceSystem(String deviceSystem) {
        this.deviceSystem = deviceSystem;
    }

    public String getDeviceSize() {
        return deviceSize;
    }

    public void setDeviceSize(String deviceSize) {
        this.deviceSize = deviceSize;
    }

    public String getDeviceNetwork() {
        return deviceNetwork;
    }

    public void setDeviceNetwork(String deviceNetwork) {
        this.deviceNetwork = deviceNetwork;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}