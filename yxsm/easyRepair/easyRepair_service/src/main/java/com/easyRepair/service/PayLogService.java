package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.PayLog;

/**
 Created by sean on 2017/1/17. */
public interface PayLogService extends ICommonService<PayLog> {
    PayLog findByOrderAndPayMode(Order order, String payMode);
}
