/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.RepairOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SuppressWarnings("JpaQlInspection")
public interface RepairOrderDao extends JpaRepository<RepairOrder, Integer>, JpaSpecificationExecutor<RepairOrder> {

    @Query("select distinct user.deviceId from User user where (user.userType = 1 or user.userType = 2 ) and user.deviceId <> '' and LENGTH(user.deviceId) > 10 ")
    List<String> getAllRepairDeviceId();

    @Query("select distinct user.deviceId from User user where user.userType = 2 and user.deviceId <> '' and LENGTH(user.deviceId) > 10 ")
    List<String> findManagerDeviceId();

  /*  @Query(value = "select repairorde0_.* as col_0_0_ from t_repair_order repairorde0_ " +
            "left join t_user user1_ on repairorde0_.user_id=user1_.id " +
            "left join t_user user2_ on repairorde0_.repair_id=user2_.id " +
            "where (user1_.user_name like %?1% or user2_.user_name like %?1%or repairorde0_.order_num like %?1%')",nativeQuery = true)
    Page<RepairOrder> findOrderByLike(String like, Pageable pageable);*/
}
