package com.easyRepair.dao;

import com.easyRepair.tabEntity.Integrals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/1. */
@SuppressWarnings("JpaQlInspection")
public interface IntegralsDao extends JpaRepository<Integrals, Long>, JpaSpecificationExecutor<Integrals> {
    @Query("select a from Integrals a where a.userId=?1 ORDER BY a.createTime desc ")
    Page<Integrals> findByUserId(Long userId, Pageable pageable);

    @Modifying
    @Query("delete from Integrals as i where i.id in (?1)")
    void deleteAllByIds(List<Long> longs1);
}
