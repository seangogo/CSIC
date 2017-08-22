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
import java.util.List;

@Entity
@Table(name = "t_repair_order")
public class RepairOrder extends IdEntity implements Serializable {
    // 订单编号
    private String orderNum;
    // 报修类型 0设备报修 1安装实施
    private String repairType;
    // 订单状态 0待处理 1已接单 2已完成 3已取消 4已评价 5已支付
    private String orderState;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // 维修人 工程师id
    @ManyToOne
    @JoinColumn(name = "repair_id")
    private User repair;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;// 订单发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date appointmentTime;// 预约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date completionTime;// 订单完成时间
    private String contactUser;// 联系人姓名
    private String contactPhone;// 联系人电话
    private String contactAddress;// 联系人地址
    private String contactLocation;// 联系人地址经纬度
    private String orderDesc;// 订单描述
    private String orderImgs;// 订单图片 按逗号分隔
    private String orderImgsThumb;// 订单图片缩略图 按逗号分隔
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;// 更新订单时间
    private String commentContent;// 订单评价内容
    private String commentStar;// 订单评星
    private Integer hasAgain;// 是否再次上门 0 不是 1是
    private Integer hasComplain;// 是否有投诉 0没有 1有
    private Integer hasComment;// 是否有评论 0没有 1有
    private Integer isRead;// 工程师是否已读该订单
    private Integer orderSource;//订单来源 0手机发布  1后台发布
    private String ccIds;//相关人id （工程师id按逗号分隔）
    private String orderExplain;//订单处理说明
    private Integer qty;//报修类型 0设备报修 台数
    @Transient
    private List<UploadFileOrders> uploadFileOrdersList;
    @Transient
    private String repairName;//临时字段(工程师名称)
    @Transient
    private List<OrderCost> orderCostList;//订单费用集合
    @Transient
    private List<OrderEngineer> orderEngineerList;//订单相关人员集合
    @Transient
    private Integer sort;//排序

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    // 发布人 用户id

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getRepair() {
        return repair;
    }

    public void setRepair(User repair) {
        this.repair = repair;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public String getContactUser() {
        return contactUser;
    }

    public void setContactUser(String contactUser) {
        this.contactUser = contactUser;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactLocation() {
        return contactLocation;
    }

    public void setContactLocation(String contactLocation) {
        this.contactLocation = contactLocation;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getOrderImgs() {
        return orderImgs;
    }

    public void setOrderImgs(String orderImgs) {
        this.orderImgs = orderImgs;
    }

    public String getOrderImgsThumb() {
        return orderImgsThumb;
    }

    public void setOrderImgsThumb(String orderImgsThumb) {
        this.orderImgsThumb = orderImgsThumb;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(String commentStar) {
        this.commentStar = commentStar;
    }

    public Integer getHasAgain() {
        return hasAgain;
    }

    public void setHasAgain(Integer hasAgain) {
        this.hasAgain = hasAgain;
    }

    public Integer getHasComplain() {
        return hasComplain;
    }

    public void setHasComplain(Integer hasComplain) {
        this.hasComplain = hasComplain;
    }

    public Integer getHasComment() {
        return hasComment;
    }

    public void setHasComment(Integer hasComment) {
        this.hasComment = hasComment;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }


    public String getCcIds() {
        return ccIds;
    }

    public void setCcIds(String ccIds) {
        this.ccIds = ccIds;
    }

    public String getOrderExplain() {
        return orderExplain;
    }

    public void setOrderExplain(String orderExplain) {
        this.orderExplain = orderExplain;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }


    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public List<OrderCost> getOrderCostList() {
        return orderCostList;
    }

    public void setOrderCostList(List<OrderCost> orderCostList) {
        this.orderCostList = orderCostList;
    }

    public List<OrderEngineer> getOrderEngineerList() {
        return orderEngineerList;
    }

    public void setOrderEngineerList(List<OrderEngineer> orderEngineerList) {
        this.orderEngineerList = orderEngineerList;
    }

    public List<UploadFileOrders> getUploadFileOrdersList() {
        return uploadFileOrdersList;
    }

    public void setUploadFileOrdersList(List<UploadFileOrders> uploadFileOrdersList) {
        this.uploadFileOrdersList = uploadFileOrdersList;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}