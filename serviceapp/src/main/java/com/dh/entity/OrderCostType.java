package com.dh.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_order_cost_type")
public class OrderCostType extends IdEntity {
	private String costName; // 费用名称
	private Integer hasReward;// 是否有提成 0没有 1有
	private Double rewardRatio;// 提成比例
	private Integer isLocked;// 是否启用 0未锁定 1已锁定
	private Long orderBy;// 排序
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;// 创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateTime;// 修改时间
	
	public OrderCostType() {
		
	}

	public OrderCostType(Long id) {
		this.id = id;
	}

	public String getCostName() {
		return costName;
	}

	public void setCostName(String costName) {
		this.costName = costName;
	}

	public Integer getHasReward() {
		return hasReward;
	}

	public void setHasReward(Integer hasReward) {
		this.hasReward = hasReward;
	}

	public Double getRewardRatio() {
		return rewardRatio;
	}

	public void setRewardRatio(Double rewardRatio) {
		this.rewardRatio = rewardRatio;
	}

	public Integer getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Integer isLocked) {
		this.isLocked = isLocked;
	}

	public Long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}