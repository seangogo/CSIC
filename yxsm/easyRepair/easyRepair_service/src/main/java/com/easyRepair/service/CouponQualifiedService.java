package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.CouponCom;
import com.easyRepair.tabEntity.CouponQualified;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/3. */
public interface CouponQualifiedService extends ICommonService<CouponQualified> {
    List<CouponQualified> findByUser_IdAndCouponCom(Long userId, CouponCom couponCom);
    
    void updateCount(CouponQualified couponQualified);

    Page<CouponQualified> page(Map<String, Object> searchParams, PageRequest pageRequest);
}
