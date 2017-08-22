package com.server.dao;

import com.server.domain.entity.OriginalMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@SuppressWarnings("JpaQlInspection")
public interface OriginalMessageDao extends JpaRepository<OriginalMessage, Long>, JpaSpecificationExecutor<OriginalMessage> {
}

