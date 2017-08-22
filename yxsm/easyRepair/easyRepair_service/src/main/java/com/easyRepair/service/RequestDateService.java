package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.RequestDate;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 Created by sean on 2017/1/16. */
public interface RequestDateService  extends ICommonService<RequestDate> {
    /*通过工程师查找所有订单请求*/
    Map<String,Object> findByTypeAndUserId(Long userId,Long orderId, String type, Pageable pageable);
    
    boolean deleteByOrderId(Long id);
    /*查找工程师发送端请求*/
    RequestDate requestOrder_IdAndUser_Id(Long orderId, Long id);
    /*查找用户发送的请求*/
    RequestDate machingOrder_IdAndUser_Id(Long orderId, Long id);
}
