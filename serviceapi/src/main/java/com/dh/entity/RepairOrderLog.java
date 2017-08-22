/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_repair_order_log")
public class RepairOrderLog extends IdEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private RepairOrder repairOrder;// 订单
    @ManyToOne
    @JoinColumn(name = "creater")
    private User creater;// 创建这个状态的人
    @ManyToOne
    @JoinColumn(name = "repair_id")
    private User repair;// 维修人 工程师
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "create_time")
    private Date createTime;// 订单发布时间
    @Column(name = "msg_type")
    private Integer msgType;//消息状态：0订单发布成功 1已接单 2订单已完成 3评价已完成 4订单已取消 5投诉  6预约时间更改 7再次上门 8更换工程师
    @Column(name = "msg_content")
    private String msgContent;//消息内容
    @Column(name = "content_type")
    private Integer contentType;//消息内容类型
    @Column(name = "comment_star")
    private String commentStar;//评星
    @Transient
    private String msgTypeStr;

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public User getCreater() {
        return creater;
    }

    public void setCreater(User creater) {
        this.creater = creater;
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

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(String commentStar) {
        this.commentStar = commentStar;
    }

    public String getMsgTypeStr() {
        return msgTypeStr;
    }

    public void setMsgTypeStr(String msgTypeStr) {
        this.msgTypeStr = msgTypeStr;
    }
}