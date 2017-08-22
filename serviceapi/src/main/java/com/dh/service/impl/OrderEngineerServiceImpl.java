package com.dh.service.impl;

import com.dh.entity.OrderEngineer;
import com.dh.repository.OrderEngineerDao;
import com.dh.service.OrderEngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/19.
 */
@Service
@Transactional(readOnly = true)
public class OrderEngineerServiceImpl implements OrderEngineerService {
    @Autowired
    private OrderEngineerDao orderEngineerDao;

    @Override
    public List<OrderEngineer> findAll() {
        return null;
    }

    @Override
    public Page<OrderEngineer> find(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public Page<OrderEngineer> find(int pageNum) {
        return null;
    }

    @Override
    public OrderEngineer getById(int id) {
        return null;
    }

    @Override
    public OrderEngineer deleteById(int id) {
        return null;
    }

    @Override
    @Transactional
    public OrderEngineer create(OrderEngineer orderEngineer) {
        return orderEngineerDao.save(orderEngineer);
    }

    @Override
    @Transactional
    public OrderEngineer update(OrderEngineer orderEngineer) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {

    }

    /*通过订单ID查询相关人员集合*/
    @Override
    public List<OrderEngineer> findByOrderId(final Integer orderId) {

        return orderEngineerDao.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public void deleteByOrderId(Integer orderId) {
        orderEngineerDao.deleteByOrderId(orderId);
    }

    @Override
    public void deleteByorderIdAnduserId(Integer orderId, Integer userId) {
        orderEngineerDao.deleteByorderIdAnduserId(orderId,userId);
    }

    @Override
    public List<Object[]> findEngineerCCList(Integer OrderId) {
        return orderEngineerDao.findEngineer(OrderId);
    }
}
