package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Fans;
import com.easyRepair.tabEntity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 Created by sean on 2016/12/19. */
public interface FansService extends ICommonService<Fans> {
    /*我的关注分页*/
    Page<User> findPageByFansUser(Long userId, Pageable pageable);
    
    /*取消/关注*/
    void cancelFollowOrFollow(boolean cancel, Long fromId, Long toId);

    Page<Fans> page(Map<String, Object> searchParams, PageRequest pageRequest);
    //fans总数
    int findBySum(Long id);//todo fans数已经存在用户详情中,操作时不确认是否更新,可删除该方法
    //查询用户,工程师是否关注
    boolean isFans(Long userId,Long RepairId);
}
