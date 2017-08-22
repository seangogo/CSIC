package com.easyRepair.dao;

import com.easyRepair.tabEntity.ServiceAppointedType;
import com.easyRepair.tabEntity.ServiceArea;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/22. */
public interface ServiceAppointedTypeDao extends JpaRepository<ServiceAppointedType, Long>, JpaSpecificationExecutor<ServiceAppointedType> {

    @Query(value = "select a from ServiceAppointedType as a where a.id in (?1)")
    List<ServiceAppointedType> findAllByIds(List<Long> longs1);

    @Modifying
    @Query(value = "delete from ServiceAppointedType a where a.id in (?1)")
    int deleteAllByIds(List<Long> longs1);




    /*@Query(value = "select a from ServiceAppointedType a left outer join ServiceType t on t.serviceAppointedType.id=a.id where a.id in (?1)")
    List<ServiceAppointedType> findAllByIds(List<Long> longs1);*/
}
