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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_repair_order_log")
public class RepairOrderLog extends IdEntity {
	private Long orderId;// 订单id
	// private Long creater;// 创建这个状态的人 user_id
	//private Long repairId;// 维修人 工程师id
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;// 订单发布时间
	private Integer msgType;// 消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉
							// 6预约时间更改 7再次上门 8更换工程师
	private String msgContent;// 消息内容
	private Integer contentType;// 消息内容类型
	private String commentStar;// 评星

	private User creater;
	private User repair;
	private String msgTypeStr;
	
	
	private Long repairId;
	private String repairName;
	private String repairPhone;
	private Integer repairType;
	private Long orderCount;
	private Long complaint;
	private Long comment;
	private Long praise;
	private Long bad;
	
	public RepairOrderLog(){
		
	}
	
	
	public RepairOrderLog(Long repairId,String repairName, String repairPhone, Integer repairType, Long orderCount, Long comment,
			Long complaint, Long praise, Long bad) {
		this.repairId = repairId;
		this.repairName = repairName;
		this.repairPhone = repairPhone;
		this.repairType = repairType;
		this.orderCount = orderCount;
		this.complaint = complaint;
		this.comment = comment;
		this.praise = praise;
		this.bad = bad;
	}
	
	public RepairOrderLog(Date createTime,String repairName, String repairPhone, Integer repairType, Long orderCount, Long comment,
			Long complaint, Long praise, Long bad) {
		this.createTime = createTime;
		this.repairName = repairName;
		this.repairPhone = repairPhone;
		this.repairType = repairType;
		this.orderCount = orderCount;
		this.complaint = complaint;
		this.comment = comment;
		this.praise = praise;
		this.bad = bad;
	}
	
	@Transient
	public Long getRepairId() {
		return repairId;
	}

	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}


	@Transient
	public String getRepairName() {
		return repairName;
	}

	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}
	@Transient
	public String getRepairPhone() {
		return repairPhone;
	}

	public void setRepairPhone(String repairPhone) {
		this.repairPhone = repairPhone;
	}
	@Transient
	public Integer getRepairType() {
		return repairType;
	}

	public void setRepairType(Integer repairType) {
		this.repairType = repairType;
	}
	@Transient
	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
	@Transient
	public Long getComplaint() {
		return complaint;
	}

	public void setComplaint(Long complaint) {
		this.complaint = complaint;
	}
	@Transient
	public Long getComment() {
		return comment;
	}

	public void setComment(Long comment) {
		this.comment = comment;
	}
	@Transient
	public Long getPraise() {
		return praise;
	}

	public void setPraise(Long praise) {
		this.praise = praise;
	}
	@Transient
	public Long getBad() {
		return bad;
	}

	public void setBad(Long bad) {
		this.bad = bad;
	}

	@Transient
	public String getMsgTypeStr() {
		return msgTypeStr;
	}

	public void setMsgTypeStr(String msgTypeStr) {
		this.msgTypeStr = msgTypeStr;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "repair_id")
	public User getRepair() {
		return repair;
	}

	public void setRepair(User repair) {
		this.repair = repair;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "creater")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getCommentStar() {
		return commentStar;
	}

	public void setCommentStar(String commentStar) {
		this.commentStar = commentStar;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}