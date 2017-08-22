package com.easyRepair.dao;

import com.easyRepair.tabEntity.Role;
import com.easyRepair.tabEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/22. */
@SuppressWarnings("JpaQlInspection")
public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    @Modifying
    @Query("delete from Role as r where r.id in (?1)")
    void deleteByIds(List<Long> longs);
   /* @Modifying
    Long deleteById(Long id);*/
}
