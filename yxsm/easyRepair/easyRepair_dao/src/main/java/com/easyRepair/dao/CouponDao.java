package com.easyRepair.dao;

import com.easyRepair.tabEntity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 Created by sean on 2016/11/25. */
public interface CouponDao extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {
    /*查询所有可以领取的优惠券*/
    @Query("select a from Coupon as a where a.getStartTime < ?1 and a.getEndTime>?1 and a.cut=0 and a.push=1 ORDER BY a.createTime asc")
    Page<Coupon> findByTime(Date date,Pageable pageable);
    /*已使用和未使用*/
    @Query("select a.coupon from UserCoupon as a where a.user.id=?1 and a.yesUse=?2 " +
            "and a.coupon.startTime< ?3 and  a.coupon.endTime > ?2 ORDER BY a.createTime asc")
    Page<Coupon> pageByStatus(Long id, Boolean status,Date now, Pageable pageable);
    /*过期优惠卷*/
    @Query("select a.coupon from UserCoupon as a where a.user.id=?1 and  a.coupon.endTime < ?2  ORDER BY a.createTime asc")
    Page<Coupon> pageByEndTime(Long id,Date now, Pageable pageable);

    @Modifying
    @Query("delete  from Coupon c where c.id in (?1)")
    void deleteByIds(List<Long> longs1);

    @Query("from Coupon c where c.id in(?1)")
    List<Coupon> getByIds(List<Long> longs);
}
