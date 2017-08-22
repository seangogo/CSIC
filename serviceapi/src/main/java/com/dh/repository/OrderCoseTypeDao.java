package com.dh.repository;

import com.dh.entity.OrderCostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/20.
 */
public interface OrderCoseTypeDao extends JpaRepository<OrderCostType, Integer>, JpaSpecificationExecutor<OrderCostType> {
    @Query("select oct from OrderCostType oct where oct.isLocked = 0  order by oct.orderBy desc")
    List<OrderCostType> getOrderCostType();
}
