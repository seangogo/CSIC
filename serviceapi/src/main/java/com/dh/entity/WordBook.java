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
@Table(name = "t_word_book")
public class WordBook extends IdEntity implements Serializable {
    private Integer wordIndex; // 字典key
    private String type; // 字典类型
    private String wordValue; // 字典 对应 key的值
    private Integer orderBy; // 排序 越大越靠前
    private Integer parentId; // 父级id
    private String description; // 描述
    private User updatUser; // 修改人 user_id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime; // 最后次修改时间


    public java.lang.Integer getWordIndex() {
        return wordIndex;
    }

    public void setWordIndex(java.lang.Integer wordIndex) {
        this.wordIndex = wordIndex;
    }

    public java.lang.String getType() {
        return type;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public java.lang.String getWordValue() {
        return wordValue;
    }

    public void setWordValue(java.lang.String wordValue) {
        this.wordValue = wordValue;
    }

    public java.lang.Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(java.lang.Integer orderBy) {
        this.orderBy = orderBy;
    }

    public java.lang.Integer getParentId() {
        return parentId;
    }

    public void setParentId(java.lang.Integer parentId) {
        this.parentId = parentId;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "updater")
    public User getUpdatUser() {
        return updatUser;
    }

    public void setUpdatUser(User updatUser) {
        this.updatUser = updatUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}