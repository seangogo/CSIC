/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("select a from User as a where a.loginName=?1")
    User findByLoginName(String loginName);


    @Modifying
    @Query("update User user set user.deviceId ='',user.deviceOs=''  where user.deviceId= ?1 ")
    void emptyDeviceId(String deviceId);

    @Query("select user.deviceId from User user where user.id = ?1 and LENGTH(user.deviceId) > 10")
    String findUserDeviceId(Integer userId);
}
