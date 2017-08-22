package com.dh.service;

import com.dh.entity.RepairOrderLog;
import com.dh.service.common.ICommonService;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/19.
 */
public interface RepairOrderLogService extends ICommonService<RepairOrderLog> {
    List<RepairOrderLog> getOrderStateByOrderId(Integer orderId);
}
