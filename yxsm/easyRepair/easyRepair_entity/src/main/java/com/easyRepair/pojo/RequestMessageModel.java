package com.easyRepair.pojo;

import java.util.Date;

/**
 Created by sean on 2017/1/16. */
public class RequestMessageModel {
    /*订单号*/
    private String orderNum;
   /*工程师名称*/
   private String nickName;
    /*接单人ID*/
   private Long userId;
    /*订单ID*/
    private Long orderId;
    /*请求时间*/
    private Date requestTime;
    
    public RequestMessageModel() {
    }
    
    public RequestMessageModel(String orderNum, String nickName, Long userId, Long orderId, Date requestTime) {
        this.orderNum = orderNum;
        this.nickName = nickName;
        this.userId = userId;
        this.orderId = orderId;
        this.requestTime = requestTime;
    }
    
    public String getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Date getRequestTime() {
        return requestTime;
    }
    
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
    
    public Long getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
