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

import com.dh.entity.OperationLog;

public interface OperationLogDao extends PagingAndSortingRepository<OperationLog, Long>, JpaSpecificationExecutor<OperationLog> {

	@Query("select log.description from OperationLog log  where log.description is not NULL and log.description <> ''  group by log.description ")
	List<String> findOperationDesc();

}
