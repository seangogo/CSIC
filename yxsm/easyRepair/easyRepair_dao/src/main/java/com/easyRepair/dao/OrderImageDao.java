package com.easyRepair.dao;

import com.easyRepair.tabEntity.OrderImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 Created by sean on 2016/11/28. */
public interface OrderImageDao extends JpaRepository<OrderImage, Long>, JpaSpecificationExecutor<OrderImage> {
    
}
