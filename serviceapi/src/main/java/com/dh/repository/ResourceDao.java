/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResourceDao extends JpaRepository<Resource, Integer>, JpaSpecificationExecutor<Resource> {
    /*@Query("select resource from Resource resource order by resource.level, resource.orderBy desc")
	List<Resource> findAllResources();
	
	@Query("select resource from Resource resource where resource.parentId = ?1")
	List<Resource> findResourcesByParentId(Long parentId);
	
	@Query("select resource from Resource resource  where resource.id in (?1)  order by resource.level, resource.orderBy desc")
	List<Resource> findResourcesByIds(List<Long> resourceIdList);
	
	@Query("select resource from Resource resource where resource.type = ?1 order by resource.level, resource.orderBy desc")
	List<Resource> findResourcesByType(String type);*/


}
