package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;


/**
 Created by sean on 2017/1/16. */
@Entity
@Table(name = "t_pay_log")
public class PayLog extends IdEntity{
    //关联订单
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //给第三方订单号
    private String orderCode;
    
    //1 支付保证金 2全额支付 3支付上门费 4 退款
    private Integer action;
    
    //支付金额
    private Float costs;
    
    //成功失败
    private Boolean status;
    //支付方式,0=微信,1=支付宝 2=账户交易
    private Integer  payMode;
    //错误码
    private String error;
    //支付时间
    private Date createTime;
    
    public PayLog() {
    }
    
    public PayLog(Order order, String orderCode, Integer action, Float costs, Boolean status, Integer payMode, String error, Date createTime) {
        this.order = order;
        this.orderCode = orderCode;
        this.action = action;
        this.costs = costs;
        this.status = status;
        this.payMode = payMode;
        this.error = error;
        this.createTime = createTime;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderCode() {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    public Integer getAction() {
        return action;
    }
    
    public void setAction(Integer action) {
        this.action = action;
    }
    
    public Float getCosts() {
        return costs;
    }
    
    public void setCosts(Float costs) {
        this.costs = costs;
    }
    
    public Boolean getStatus() {
        return status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public Integer getPayMode() {
        return payMode;
    }
    
    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}