package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_order_cost")
public class OrderCost extends IdEntity {
	private Long orderId; // 订单id

	private OrderCostType costType; // 费用类型
	private Double cost; // 费用
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;// 创建时间

	private List<OrderCostType> orderCostTypes;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	// JPA 基于role_id列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "cost_type")
	public OrderCostType getCostType() {
		return costType;
	}

	public void setCostType(OrderCostType costType) {
		this.costType = costType;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Transient
	public List<OrderCostType> getOrderCostTypes() {
		return orderCostTypes;
	}

	public void setOrderCostTypes(List<OrderCostType> orderCostTypes) {
		this.orderCostTypes = orderCostTypes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}