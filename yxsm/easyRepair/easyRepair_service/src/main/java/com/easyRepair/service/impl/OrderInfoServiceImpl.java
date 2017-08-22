package com.easyRepair.service.impl;

import com.easyRepair.dao.OrderInfoDao;
import com.easyRepair.service.OrderInfoService;
import com.easyRepair.tabEntity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/11/25. */
@Service
@Transactional(readOnly = true)
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;
    
    public List<OrderInfo> findAll() {
        return null;
    }
    
    public Page<OrderInfo> find(int i, int i1) {
        return null;
    }
    
    public Page<OrderInfo> find(int i) {
        return null;
    }
    
    public OrderInfo getById(long l) {
        return null;
    }
    
    public OrderInfo deleteById(long l) {
        return null;
    }
    
    @Transactional
    public OrderInfo create(OrderInfo orderInfo) {
        return orderInfoDao.saveAndFlush(orderInfo);
    }
    
    public OrderInfo update(OrderInfo orderInfo) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    
}
