package com.dh.repository;

import com.dh.entity.OrderTypeCostType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Coolkid on 2016/9/29.
 */
public interface OrderTypeCostTypeDao extends PagingAndSortingRepository<OrderTypeCostType, Long>, JpaSpecificationExecutor<OrderTypeCostType> {
    @Query("select distinct otct.repairTypeId from OrderTypeCostType otct ")
    List<String> findAllOrderType();

    @Query("select otct from OrderTypeCostType otct WHERE otct.repairTypeId=?1 order by otct.orderCostType.orderBy asc")
    List<OrderTypeCostType> getAllCostByTypeId(String typeId);
}
