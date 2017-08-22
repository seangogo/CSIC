package com.easyRepair.dao;

import com.easyRepair.tabEntity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 Created by sean on 2016/11/14. */
@SuppressWarnings("JpaQlInspection")
public interface UserInfoDao extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
    int countByNickNameOrEmail(String nickName, String email);
  /*  @Query("SELECT info From UserInfo info where info.user.id=?1")*/
    
}
