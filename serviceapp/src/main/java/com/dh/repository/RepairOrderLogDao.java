/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.RepairOrderLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RepairOrderLogDao extends PagingAndSortingRepository<RepairOrderLog, Long>, JpaSpecificationExecutor<RepairOrderLog> {

	List<RepairOrderLog> findByOrderIdOrderByCreateTimeDesc(Long id);
}
