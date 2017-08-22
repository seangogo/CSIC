package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Integrals;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 Created by sean on 2016/12/1. */
public interface IntegralsService extends ICommonService<Integrals> {
    Page<Integrals> findByUserId(Long userId, Pageable pageable);

    Page<Integrals> page(PageRequest pageRequest);

    void createOrUpdate(Integrals integrals);
}
