package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/25. */
public interface CouponService extends ICommonService<Coupon> {
    
    Page<Coupon> findpageByTime(Pageable pageable);
    
    Map<String,Object> page(Long id, Integer status, PageRequest pageRequest);

    Page<Coupon> page(Map<String, Object> searchParams, PageRequest pageRequest);

    List<Coupon> getByIds(long[] arrayId);
}
