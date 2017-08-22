package com.dh.service.order;

import com.dh.entity.OrderCost;
import com.dh.entity.OrderTypeCostType;
import com.dh.repository.OrderCostDao;
import com.dh.repository.OrderTypeCostTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrderCostService {
    @Autowired
    private OrderCostDao orderCostDao;
    @Autowired
    private OrderTypeCostTypeDao orderTypeCostTypeDao;

    public List<OrderCost> findByOrderIdOrderByIdAsc(Long orderId) {
        return orderCostDao.findByOrderIdOrderByIdAsc(orderId);
    }

    public List<String> findAllOrderType() {
        return orderTypeCostTypeDao.findAllOrderType();
    }

    /*根据订单类型ID 获得所有可选费用集合*/
    public List<OrderTypeCostType> getAllCostByTypeId(String typeId) {
        return orderTypeCostTypeDao.getAllCostByTypeId(typeId);
    }

    /*保存*/
    public OrderCost save(OrderCost orderCost) {
        return orderCostDao.save(orderCost);
    }

    public int delOrderCostByOId(Long orderId) {
        return orderCostDao.delOrderCostByOId(orderId);
    }
}
