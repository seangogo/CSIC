package com.easyRepair.service;

import com.easyRepair.tabEntity.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by sean on 2017/2/15.
 */
public interface OperationLogService {
    void save(OperationLog log);

    Page<OperationLog> page(Map<String, Object> searchParams, Pageable pageable);
}
