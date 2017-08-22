package com.easyRepair.dao;

import com.easyRepair.tabEntity.User;
import com.easyRepair.tabEntity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/8. */
@SuppressWarnings("JpaQlInspection")
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Modifying
    @Query("update User u set isLocked=(?1) where u.id =?2")
    void lockByIds(Boolean flag, Long ids);

    List<User> findByLoginName(String loginName);

    List<User> findUserByLoginNameAndTypeAndIsLocked(String loginName, String type, boolean isLocked);

    @Query("select u from User u where u.id in (?1)")
    List<User> findAllByIds(List<Long> userIds);

    //left join u.userInfo left join u.userLoginInfo
    // @Query("select u from UserInfo u ")
    Page<User> findAll(Pageable pageable);

    List<User> findByLoginNameAndType(String loginName, String type);

    /**
     * 锁定用户
     * @param id
     */
    @Modifying
    @Query("update User user set user.isLocked=1 where user.id =?1")
    void lock(Long id);

    User findFirstByLoginNameAndTypeAndIsLocked(String loginName, String type, boolean b);

    @Query("select new Map(u,u.userInfo,u.userLoginInfo) from User u  where u.userLoginInfo.isLogin = true and u.type=1 order by (POWER(MOD(ABS(?1 - u.userLoginInfo.lastLng),360),2) + POWER(ABS(?2 - u.userLoginInfo.lastLat),2)) ")
    List<Map<String,Object>> findTest(Double lng, Double lat);

    @Query("select a.userInfo from User a where a.id=?1")
    UserInfo findUserInfoById(Long id);
}
