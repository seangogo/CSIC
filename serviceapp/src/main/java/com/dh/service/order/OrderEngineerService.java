package com.dh.service.order;

import com.dh.entity.OrderEngineer;
import com.dh.entity.User;
import com.dh.repository.OrderEngineerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrderEngineerService {
    @Autowired
    private OrderEngineerDao orderEngineerDao;

    public List<User> findEngineerByOrderId(Long orderId) {
        return orderEngineerDao.findEngineerByOrderId(orderId);
    }

    public List<Long> findEngineerByOrderIdNotRepair(Long orderId) {
        return orderEngineerDao.findUseIdByOrderId(orderId);
    }

    public List<String> getOrderEngineerByOId(Long orderId) {
        return orderEngineerDao.getOrderEngineerByOId(orderId);
    }

    public void save(OrderEngineer entity) {
        orderEngineerDao.save(entity);
    }

    public void delete(Long id) {
        orderEngineerDao.delete(id);
    }

    public void deleteAllCCByOderId(Long orderId) {
        orderEngineerDao.deleteAllCCByOderId(orderId);
    }
}
