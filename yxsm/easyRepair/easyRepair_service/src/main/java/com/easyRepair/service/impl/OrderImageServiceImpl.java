package com.easyRepair.service.impl;

import com.easyRepair.dao.OrderImageDao;
import com.easyRepair.service.OrderImageService;
import com.easyRepair.tabEntity.OrderImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/11/28. */
@Service
@Transactional(readOnly = true)
public class OrderImageServiceImpl implements OrderImageService {
    @Autowired
    private OrderImageDao orderImageDao;
    
    public List<OrderImage> findAll() {
        return null;
    }
    
    public Page<OrderImage> find(int i, int i1) {
        return null;
    }
    
    public Page<OrderImage> find(int i) {
        return null;
    }
    
    public OrderImage getById(long l) {
        return null;
    }
    
    public OrderImage deleteById(long l) {
        return null;
    }
    
    @Transactional
    public OrderImage create(OrderImage orderImage) {
        return orderImageDao.saveAndFlush(orderImage);
    }
    
    public OrderImage update(OrderImage orderImage) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
}
