package com.easyRepair.dao;

import com.easyRepair.tabEntity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by sean on 2017/2/15.
 */
public interface OperationLogDao  extends JpaRepository<OperationLog, Long>, JpaSpecificationExecutor<OperationLog> {
}
