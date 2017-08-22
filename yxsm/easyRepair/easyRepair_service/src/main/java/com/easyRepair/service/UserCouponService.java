package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.UserCoupon;

import java.util.List;

/**
 Created by sean on 2016/11/25. */
public interface UserCouponService extends ICommonService<UserCoupon> {
    List<UserCoupon> findByUser_IdAndCoupon_Id(Long userId, Long couponId);
    
    int findCountByUserId(Long id, Coupon coupon);
}
