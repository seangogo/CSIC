package com.dh.service;

import com.dh.entity.RepairOrder;
import com.dh.entity.UploadFileOrders;
import com.dh.service.common.ICommonService;

import java.util.List;

/**
 * Created by Coolkid on 2016/10/18.
 */
public interface UploadFileOrdersService  extends ICommonService<UploadFileOrders> {
    List<UploadFileOrders> findByRepairOrder(RepairOrder order);
}
