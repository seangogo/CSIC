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

import com.dh.entity.Resource;

public interface ResourceDao extends PagingAndSortingRepository<Resource, Long>,JpaSpecificationExecutor<Resource> {

	
	@Query("select resource from Resource resource order by resource.level, resource.orderBy desc")
	List<Resource> findAllResources();
	
	@Query("select resource from Resource resource where resource.parentId = ?1 and resource.resourceType=0 ")
	List<Resource> findMenuResourcesByParentId(Long parentId);
	
	@Query("select resource from Resource resource where resource.parentId = ?1  ")
	List<Resource> findResourcesByParentId(Long parentId);
	
	@Query("select resource from Resource resource  where resource.id in (?1)  order by resource.level, resource.orderBy desc")
	List<Resource> findResourcesByIds(List<Long> resourceIdList);
	
	/*@Query("select resource from Resource resource  where resource.id in (?1) and resource.resourceType = 0  order by resource.level, resource.orderBy desc")
	List<Resource> finMenuByResourceIds(List<Long> resourceIdList);*/

	@Query("select resource from Resource resource where resource.type = ?1 order by resource.level, resource.orderBy desc , resource.resourceType ")
	List<Resource> findResourcesByType(String type);


	@Query("select r.type from Resource r where r.resUrl like %?1% ")
	Integer findTabeType(String url);
	
	
}
