package com.easyRepair.service.impl;

import com.easyRepair.dao.UserCouponDao;
import com.easyRepair.service.UserCouponService;
import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.UserCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/11/25. */
@Service
@Transactional(readOnly = true)
public class UserCouponServiceImpl implements UserCouponService {
    @Autowired
    private UserCouponDao userCouponDao;
    
    public List<UserCoupon> findAll() {
        return null;
    }
    
    public Page<UserCoupon> find(int i, int i1) {
        return null;
    }
    
    public Page<UserCoupon> find(int i) {
        return null;
    }
    
    public UserCoupon getById(long l) {
        return null;
    }
    
    public UserCoupon deleteById(long l) {
        return null;
    }
    @Transactional
    public UserCoupon create(UserCoupon userCoupon) {
        return userCouponDao.saveAndFlush(userCoupon);
    }
    
    public UserCoupon update(UserCoupon userCoupon) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    public List<UserCoupon> findByUser_IdAndCoupon_Id(Long userId, Long couponId) {
        return userCouponDao.findByUser_IdAndCoupon_Id(userId, couponId);
    }
    
    public int findCountByUserId(Long id, Coupon coupon) {
        
        return userCouponDao.findCountByUserId(id,coupon);
    }
}
