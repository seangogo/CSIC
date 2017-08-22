package com.dh.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Coolkid on 2016/9/29.
 */
@Entity
@Table(name = "t_order_cost_type_repairtype")
public class OrderTypeCostType extends IdEntity {
    private String repairTypeId;

    private OrderCostType orderCostType;

    public String getRepairTypeId() {
        return repairTypeId;
    }

    public void setRepairTypeId(String repairTypeId) {
        this.repairTypeId = repairTypeId;
    }
    @ManyToOne
    @JoinColumn(name = "cost_type_id")
    public OrderCostType getOrderCostType() {
        return orderCostType;
    }

    public void setOrderCostType(OrderCostType orderCostType) {
        this.orderCostType = orderCostType;
    }
}
