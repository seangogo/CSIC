package com.easyRepair.dao;

import com.easyRepair.tabEntity.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/11/22. */
@SuppressWarnings("JpaQlInspection")
public interface UserPositionDao extends JpaRepository<UserPosition, Long>, JpaSpecificationExecutor<UserPosition> {
    @Query(value = "SELECT a.id ,a.lng , a.lat , (POWER(MOD(ABS(?1  - a.lng),360),2) + POWER(ABS(?2 - a.lat),2)) AS distance FROM t_user_position a ,(SELECT user_id, max(create_time) max_day FROM t_user_position  GROUP BY user_id) b  " +
            "WHERE a.user_id = B.user_id AND a.create_time = b.max_day AND a.user_id in (?3)", nativeQuery = true)
    List<Object[]> orderByDiskInUserId(Double lng, Double lat, String userIds);

}
