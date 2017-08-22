package com.dh.service;

import com.dh.entity.OrderEngineer;
import com.dh.service.common.ICommonService;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/19.
 */
public interface OrderEngineerService extends ICommonService<OrderEngineer> {
    List<OrderEngineer> findByOrderId(Integer orderId);

    void deleteByOrderId(Integer orderId);

    void deleteByorderIdAnduserId(Integer orderId,Integer repairId);

    List<Object[]> findEngineerCCList(Integer id);
}
