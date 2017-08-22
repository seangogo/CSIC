package com.dh.service.impl;

import com.dh.entity.OrderCostType;
import com.dh.repository.OrderCoseTypeDao;
import com.dh.service.OrderCostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/20.
 */
@Service
@Transactional(readOnly = true)
public class OrderCostTypeServiceImpl implements OrderCostTypeService {
    @Autowired
    private OrderCoseTypeDao orderCoseTypeDao;

    @Override
    public List<OrderCostType> findAll() {
        return null;
    }

    @Override
    public Page<OrderCostType> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<OrderCostType> find(int pageNum) {
        return null;
    }

    @Override
    public OrderCostType getById(int id) {
        return null;
    }

    @Override
    public OrderCostType deleteById(int id) {
        return null;
    }

    @Override
    public OrderCostType create(OrderCostType orderCostType) {
        return null;
    }

    @Override
    public OrderCostType update(OrderCostType orderCostType) {
        return null;
    }

    @Override
    public void deleteAll(int[] ids) {

    }

    @Override
    public List<OrderCostType> getOrderCostType() {
        return orderCoseTypeDao.getOrderCostType();
    }
}
