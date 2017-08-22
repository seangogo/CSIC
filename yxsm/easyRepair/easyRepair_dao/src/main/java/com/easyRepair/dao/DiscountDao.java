package com.easyRepair.dao;

import com.easyRepair.tabEntity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by WJY on 2017/3/3.
 */
public interface DiscountDao extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {

    Discount findFirstByType(Integer type);
}
