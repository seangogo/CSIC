package com.easyRepair.dao;

import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.PayLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 Created by sean on 2017/1/17. */
public interface PayLogDao  extends JpaRepository<PayLog, Long>, JpaSpecificationExecutor<PayLog> {
    @Query("select a from PayLog as a where a.order=?1 and a.payMode=?2 and a.action=1")
    PayLog findByOrderAndPayMode(Order order, String payMode);
}
