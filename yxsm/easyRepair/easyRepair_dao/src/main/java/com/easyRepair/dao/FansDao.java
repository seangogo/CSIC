package com.easyRepair.dao;

import com.easyRepair.tabEntity.Fans;
import com.easyRepair.tabEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/19. */
@SuppressWarnings("JpaQlInspection")
public interface FansDao extends JpaRepository<Fans, Long>, JpaSpecificationExecutor<Fans> {
    //根据用户ID查询关注的用户分页
    @Query("select a.followUser from Fans a where a.fansUser.id=?1")
    Page<User> findPageByFansUser(Long userId, Pageable pageable);
    //根据关注者和被关注者查询记录
    Fans findByFansUser_IdAndFollowUser_Id(Long fromId, Long toId);
    //查询工程师fans总数
    @Query("select fans from Fans fans where fans.followUser.id=?1")
    List<Fans> findSum(Long id);
}
