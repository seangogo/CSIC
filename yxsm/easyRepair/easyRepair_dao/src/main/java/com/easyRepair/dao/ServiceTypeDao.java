package com.easyRepair.dao;

import com.easyRepair.tabEntity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/11/22. */
public interface ServiceTypeDao extends JpaRepository<ServiceType, Long>, JpaSpecificationExecutor<ServiceType> {
    List<ServiceType> findByServiceAppointedType_id(Long serviceAppointedTypeId);

    @Modifying
    @Query(value = "delete from ServiceType as a where a.id in (?1)")
    int deleteAllByIds(List<Long> ids);

    @Query(value = "select a from ServiceType as a where a.id in (?1)")
    List<ServiceType> findAllByIds(List<Long> longs);

    @Query(value = "select a from ServiceType a where a.serviceAppointedType.id in (?1)")
    List<ServiceType> findByAppointedIds(List<Long> arrayId);

    @Query("select ta.serviceTypeId from ServiceTypeArea ta where ta.serviceAreaId=?1")
    List<Long> findTypeIdsByAreaId(Long id);

    @Query(value="select t from ServiceType as t where t.id in (select ta.serviceTypeId from ServiceTypeArea as ta where ta.serviceAreaId=?1)")
    List<ServiceType> findByAreaId(Long id);

    @Query("from ServiceType s where s.id in (?1)")
    List<ServiceType> getByIds(List<Long> longs);
}
