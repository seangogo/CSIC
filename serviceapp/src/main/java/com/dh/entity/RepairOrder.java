/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_repair_order")
public class RepairOrder extends IdEntity {
	private String orderNum;// 订单编号
	private Integer repairType;// 报修类型 0设备报修 1安装实施
	private Integer orderState;// 订单状态 0待处理 1已接单 2已完成 3已取消 4已评价 5已取消订单
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;// 订单发布时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date appointmentTime;// 预约时间
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
	private User user;
	private User repair;
	private Integer isRead; //工程师是否查看订单  0未读 1已读
	private Integer orderSource;//订单来源 0手机发布  1后台发布
	private String orderExplain;//订单处理说明
	private String ccIds; //相关人id
	private Integer qty;//设备维修类型订单的维修数量
	private List<OrderCost> orderCostList;//订单费用集合
	private List<User> orderEngineerList;//订单相关人员集合
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

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date completionTime;// 订单完成时间

	public Date getCompletionTime() {
		return completionTime;
	}

	public void setCompletionTime(Date completionTime) {
		this.completionTime = completionTime;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "repair_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getRepair() {
		return repair;
	}

	public void setRepair(User repair) {
		this.repair = repair;
	}

	public Integer getHasComment() {
		return hasComment;
	}

	public void setHasComment(Integer hasComment) {
		this.hasComment = hasComment;
	}

	public String getContactLocation() {
		return contactLocation;
	}

	public void setContactLocation(String contactLocation) {
		this.contactLocation = contactLocation;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getRepairType() {
		return repairType;
	}

	public void setRepairType(Integer repairType) {
		this.repairType = repairType;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	@Transient
	public Long getUserId() {
		if(null == user){
			return null;
		}
		return user.getId();
	}

	@Transient
	public Long getRepairId() {
		if(null == repair){
			return null;
		}
		return repair.getId();
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

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}
	@Transient
	public List<OrderCost> getOrderCostList() {
		return orderCostList;
	}

	public void setOrderCostList(List<OrderCost> orderCostList) {
		this.orderCostList = orderCostList;
	}
	@Transient
	public List<User> getOrderEngineerList() {
		return orderEngineerList;
	}

	public void setOrderEngineerList(List<User> orderEngineerList) {
		this.orderEngineerList = orderEngineerList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}