/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_invite_code")
public class InviteCode extends IdEntity implements Serializable {
    private String codeNum;//邀请码编号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "create_time")
    private Date createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "use_time")
    private Date useTime;//认证时间
    @ManyToOne
    @JoinColumn(name = "from_id")
    private User fromUser;//创建邀请码的人
    @ManyToOne
    @JoinColumn(name = "to_id")
    private User toUser;//使用邀请码的人
    @Column(name = "is_use")
    private Integer isUse;//是否使用

    public java.lang.String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(java.lang.String codeNum) {
        this.codeNum = codeNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public com.dh.entity.User getFromUser() {
        return fromUser;
    }

    public void setFromUser(com.dh.entity.User fromUser) {
        this.fromUser = fromUser;
    }

    public com.dh.entity.User getToUser() {
        return toUser;
    }

    public void setToUser(com.dh.entity.User toUser) {
        this.toUser = toUser;
    }

    public java.lang.Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(java.lang.Integer isUse) {
        this.isUse = isUse;
    }
}