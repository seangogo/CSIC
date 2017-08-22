/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.InviteCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface InviteCodeDao extends JpaRepository<InviteCode, Integer>, JpaSpecificationExecutor<InviteCode> {

    @Query("select inviteCode from InviteCode inviteCode where inviteCode.codeNum = ?1")
    InviteCode getInviteCode(String inviteCode);
}
