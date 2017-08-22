package com.easyRepair.dao;

import com.easyRepair.tabEntity.RequestDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 Created by sean on 2017/1/16. */
@SuppressWarnings("JpaQlInspection")
public interface RequestDateDao extends JpaRepository<RequestDate, Long>, JpaSpecificationExecutor<RequestDate> {
    
    @Query("select a from RequestDate a where a.user.id=?1 and a.matching=1  ORDER BY a.createTime desc")
    Page<RequestDate> findByRepaiId(Long userId, Pageable pageable);
    
    @Query("select a from RequestDate a where a.order.id=?1 and a.matching=0  ORDER BY a.createTime desc")
    Page<RequestDate> findByOrder_Id(Long orderId,Pageable pageable);
    
    @Modifying
    @Query(value = "delete from t_request_date  where order_id=?1",nativeQuery = true)
    int deleteByOrderId(Long id);
    
    @Query("select a from RequestDate a where a.order.id=?1 and a.matching=0 and a.user.id=?2")
    RequestDate requestOrder_IdAndUser_Id(Long orderId, Long id);
    
    @Query("select a from RequestDate a where a.order.id=?1 and a.matching=1 and a.user.id=?2")
    RequestDate matchingOrder_IdAndUser_Id(Long orderId, Long id);
}
