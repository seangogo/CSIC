package com.easyRepair.dao;

import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.CouponCom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2017/1/3. */
public  interface CouponComDao extends JpaRepository<CouponCom, Long>, JpaSpecificationExecutor<CouponCom>{
    @Modifying
    @Query("update Coupon as a set a.number=a.number-1 where a=?1")
    void updateNumber(Coupon coupon);

    @Modifying
    @Query("delete from CouponCom c where c.id in (?1)")
    void deleteAllByIds(List<Long> longs1);

    @Query("from CouponCom c where c.id in (?1)")
    List<CouponCom> findByIds(List<Long> longs);
}
