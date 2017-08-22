package com.easyRepair.dao;

import com.easyRepair.tabEntity.CouponCom;
import com.easyRepair.tabEntity.CouponQualified;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2017/1/3. */
public interface CouponQualifiedDao extends JpaRepository<CouponQualified, Long>, JpaSpecificationExecutor<CouponQualified> {
    List<CouponQualified> findByUser_IdAndCouponCom(Long userId, CouponCom couponCom);
    @Modifying
    @Query("update CouponQualified as a set a.count=a.count-1 where a=?1")
    void updateCount(CouponQualified couponQualified);

    @Modifying
    @Query("delete from CouponQualified c where c.id in(?1)")
    void deleteByIds(List<Long> longs1);
}
