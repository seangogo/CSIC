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
import com.dh.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long>,JpaSpecificationExecutor<Role> {
	
	@Query("select role.id,role.roleName,role.description,role.updateTime from Role role")
	List<Role> findRoles();

	Role findByRoleName(String roleName);
}
