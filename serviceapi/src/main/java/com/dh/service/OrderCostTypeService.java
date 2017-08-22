package com.dh.service;

import com.dh.entity.OrderCostType;
import com.dh.service.common.ICommonService;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/20.
 */
public interface OrderCostTypeService extends ICommonService<OrderCostType> {

    List<OrderCostType> getOrderCostType();
}
