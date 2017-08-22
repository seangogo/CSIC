package com.easyRepair.dao;

import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/25. */
public interface UserCouponDao extends JpaRepository<UserCoupon, Long>, JpaSpecificationExecutor<UserCoupon> {
    List<UserCoupon> findByUser_IdAndCoupon_Id(Long userId, Long couponId);
    
    @Query("select count(a) from UserCoupon as a where a.user.id=?1 and a.coupon=?2")
    int findCountByUserId(Long id, Coupon coupon);
}
