package com.easyRepair.dao;

import com.easyRepair.tabEntity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 Created by sean on 2016/11/25. */
public interface OrderInfoDao extends JpaRepository<OrderInfo, Long>, JpaSpecificationExecutor<OrderInfo> {
    /*查询发布时间在系统时间30分钟之前并且还未付款的订单并将他们的状态变为过期（状态码10）*/
    
}
