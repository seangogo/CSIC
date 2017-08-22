/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.RepairOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepairOrderLogDao extends JpaRepository<RepairOrderLog, Integer>, JpaSpecificationExecutor<RepairOrderLog> {
    @Query("select rol from RepairOrderLog rol where rol.repairOrder.id = ?1  order by rol.id desc ")
    List<RepairOrderLog> getOrderStateByOrderId(Integer orderId);

	/*@Query("select rol from RepairOrderLog rol where rol.orderId = ?1  order by rol.createTime desc")
    List<RepairOrderLog> getOrderStateByOrderId(Long orderId);*/

}
