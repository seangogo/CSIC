/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.OrderCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderCostDao extends JpaRepository<OrderCost, Integer>, JpaSpecificationExecutor<OrderCost> {

    @Query("select oc from OrderCost oc where oc.repairOrder.id = ?1")
    List<OrderCost> getOrderCostByOid(int orderId);
}
