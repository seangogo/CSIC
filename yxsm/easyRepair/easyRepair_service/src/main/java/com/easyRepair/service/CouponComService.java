package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.CouponCom;
import com.easyRepair.tabEntity.CouponQualified;
import com.easyRepair.tabEntity.UserCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/3. */
public interface CouponComService  extends ICommonService<CouponCom> {
    UserCoupon doleCoupon(CouponQualified couponQualified, Long userId, Coupon coupon);
    
    UserCoupon doleCouponAll(Long userId, Coupon coupon);

    Page<CouponCom> page(Map<String, Object> searchParams, PageRequest pageRequest);

    List<CouponCom> findByIds(long[] arrayId);
}
