package com.easyRepair.service.impl;

import com.easyRepair.dao.PayLogDao;
import com.easyRepair.service.PayLogService;
import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2017/1/17. */
@Service
@Transactional(readOnly = true)
public class PaylogServiceImpl implements PayLogService {
    @Autowired
    private PayLogDao payLogDao;
    
    public List<PayLog> findAll() {
        return null;
    }
    
    public Page<PayLog> find(int i, int i1) {
        return null;
    }
    
    public Page<PayLog> find(int i) {
        return null;
    }
    
    public PayLog getById(long l) {
        return payLogDao.findOne(l);
    }
    
    public PayLog deleteById(long l) {
        return null;
    }
    @Transactional
    public PayLog create(PayLog payLog) {
        return payLogDao.saveAndFlush(payLog);
    }
    @Transactional
    public PayLog update(PayLog payLog) {
        return payLogDao.saveAndFlush(payLog);
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    public PayLog findByOrderAndPayMode(Order order, String payMode) {
        /*查询支付保证金的记录*/
        return payLogDao.findByOrderAndPayMode(order,payMode);
    }
}
