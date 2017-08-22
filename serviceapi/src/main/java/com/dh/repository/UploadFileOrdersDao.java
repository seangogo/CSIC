package com.dh.repository;

import com.dh.entity.RepairOrder;
import com.dh.entity.UploadFileOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Coolkid on 2016/10/14.
 */
public interface UploadFileOrdersDao extends JpaRepository<UploadFileOrders, Integer>, JpaSpecificationExecutor<UploadFileOrders>{
    List<UploadFileOrders> findByRepairOrder(RepairOrder repairOrder);
}
