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

import com.dh.entity.Group;

public interface GroupDao extends PagingAndSortingRepository<Group, Long>,JpaSpecificationExecutor<Group> {
	
	
	@Query("select g from Group as g where g.parentId = ?1")
	List<Group> findGroupsByParentId(Long parentId);
	
}
