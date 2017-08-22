/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_order_cost_type")
public class OrderCostType extends IdEntity {
    private String costName; // 费用名称
    private Integer hasReward;// 是否有提成 0没有 1有
    private Double rewardRatio;// 提成比例
    private Integer isLocked;// 是否启用 0未锁定 1已锁定
    private Integer orderBy;// 排序
    /**
     * 添加时间
     */
    /*@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    public java.lang.String getCostName() {
        return costName;
    }

    public void setCostName(java.lang.String costName) {
        this.costName = costName;
    }

    public java.lang.Integer getHasReward() {
        return hasReward;
    }

    public void setHasReward(java.lang.Integer hasReward) {
        this.hasReward = hasReward;
    }

    public java.lang.Double getRewardRatio() {
        return rewardRatio;
    }

    public void setRewardRatio(java.lang.Double rewardRatio) {
        this.rewardRatio = rewardRatio;
    }

    public java.lang.Integer getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(java.lang.Integer isLocked) {
        this.isLocked = isLocked;
    }

    public java.lang.Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(java.lang.Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}