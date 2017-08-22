package com.easyRepair.dao;

import com.easyRepair.tabEntity.ServiceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/22. */
@SuppressWarnings("JpaQlInspection")
public interface ServiceAreaDao extends JpaRepository<ServiceArea, Long>, JpaSpecificationExecutor<ServiceArea> {
    @Modifying
    @Query(value = "delete from ServiceArea as  a where a.id in (?1)")
    int deleteAllByIds(List<Long> ids);

    @Query("select a from ServiceArea as a where a.id in (select ta.serviceAreaId from ServiceTypeArea as ta where ta.serviceTypeId=?1)")
    List<ServiceArea> findByTypeId(Long id);

    List<ServiceArea> findByIdIn(List<Long> ids);
}
