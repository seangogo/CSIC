package com.easyRepair.dao;

import com.easyRepair.tabEntity.Notify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 Created by sean on 2017/1/18. */
public interface NotifyDao extends JpaRepository<Notify, Long>, JpaSpecificationExecutor<Notify> {
}
