package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.User;
import com.easyRepair.tabEntity.UserLoginInfo;
import common.core.bean.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/8. */
public interface UserService extends ICommonService<User> {
    //验证登录
    List<User> findUserByLoginNameAndTypeAndIsLocked(String loginName, String type, boolean isLocked);
    
    //用户注册
    Result register(User user);
    
    //检验密码
    boolean checkUserPassword(User user, String password);


    Page<User> page(Map<String,Object> searchParams, PageRequest pageRequest);


    Result createAndUpdate(User user);

    boolean checkUserLoginNameAndType(User user);

    //找回密码
    Result findPassword(User user);

    Result apiLogin(User user, UserLoginInfo userLoginInfo);

    Result getIndex();
    //锁定单条记录
    void lock(Boolean isLocked, Long id);
}
