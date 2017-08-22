package com.easyRepair.dao;

import com.easyRepair.tabEntity.User;
import com.easyRepair.tabEntity.UserRepairInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/22. */
@SuppressWarnings("JpaQlInspection")
public interface UserRepairInfoDao extends JpaRepository<UserRepairInfo, Long>, JpaSpecificationExecutor<UserRepairInfo> {
    List<UserRepairInfo> findByIdIn(List<Long> repairInfoIds);

    UserRepairInfo findByUser_Id(Long userId);

//   @Query("select u from User u left join fetch UserRepairInfo ur left join fetch UserLoginInfo info where info.isLogin = true order by (POWER(MOD(ABS(?1 - info.lastLng),360),2) + POWER(ABS(?2 - info.lastLat),2))")
    @Query("select u from UserRepairInfo u where u.user.userLoginInfo.isLogin = true order by (POWER(MOD(ABS(?1 - u.user.userLoginInfo.lastLng),360),2) + POWER(ABS(?2 - u.user.userLoginInfo.lastLat),2))")
    List<UserRepairInfo> findRecommend(Double lng, Double lat);

    List<UserRepairInfo> findByUserIn(List<User> users);
    //查询工程师认证信息
   // @Query("select u from UserRepairInfo u JOIN FETCH u.qualificationImages  where u.user.id=?1")
    @Query("select new UserRepairInfo(u.identityCode,u.profile,u.jobTitle,u.realName) from UserRepairInfo u  where u.user.id=?1")
    UserRepairInfo findByUserId(Long id);
}
