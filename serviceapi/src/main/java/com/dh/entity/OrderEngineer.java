package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_engineer")
public class OrderEngineer extends IdEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private RepairOrder repairOrder;// 订单
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public RepairOrder getRepairOrder() {
        return repairOrder;
    }

    public void setRepairOrder(RepairOrder repairOrder) {
        this.repairOrder = repairOrder;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}