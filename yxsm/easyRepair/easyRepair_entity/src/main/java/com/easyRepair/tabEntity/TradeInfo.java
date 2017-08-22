package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sean on 2016/12/22.
 */
@Entity
@Table(name = "t_trade_info")
public class TradeInfo extends IdEntity {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;//操作人
    private Integer payModel;//支付方式 1 平台 2 支付宝 3微信
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;//操作人
    private Double amount;//变化金额
    private Double balance;//剩余余额
    private String tradeNo;//流水号
    private Integer type;//类型 ;1充值 2提现 3交易
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime; //付款时间
    private String remarks;//备注

    public TradeInfo() {
    }

    public TradeInfo(User user, Integer payModel, Double amount, Double balance, Integer type, Date createTime,String tradeNo, String remarks) {
        this.user = user;
        this.payModel = payModel;
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.tradeNo = tradeNo;
        this.createTime = createTime;
        this.remarks = remarks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getPayModel() {
        return payModel;
    }

    public void setPayModel(Integer payModel) {
        this.payModel = payModel;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
