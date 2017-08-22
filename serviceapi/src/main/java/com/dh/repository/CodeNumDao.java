/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.CodeNum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CodeNumDao extends PagingAndSortingRepository<CodeNum, Long>, JpaSpecificationExecutor<CodeNum> {

    @Query("select code from CodeNum code  where code.codeParam = ?1 ")
    CodeNum findByCodeParam(String codeParam);
}
