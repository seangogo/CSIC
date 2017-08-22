/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dh.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
	User findByLoginName(String loginName);

	@Query("select user.deviceId from User user where user.id = ?1 and LENGTH(user.deviceId) > 10")
	String findUserDeviceId(Long userId);

	@Query("select user from User user where user.userType= 1 or user.userType=2")
	List<User> getRepairList();

	@Query("select count(u) from User u where u.role.id = ?1")
	Long countByRoleId(long roleId);
}
