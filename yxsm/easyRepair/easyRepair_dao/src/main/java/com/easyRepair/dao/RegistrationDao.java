package com.easyRepair.dao;

import com.easyRepair.tabEntity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/24. */
public interface RegistrationDao extends JpaRepository<Registration, Long>, JpaSpecificationExecutor<Registration> {
    @Query(value = "SELECT r1.* FROM t_registration r1 WHERE  r1.user_id=?1 AND date(create_time) = curdate()", nativeQuery = true)
    List<Registration> getRegistrationByUserId(Long userId);
}
