package com.easyRepair.dao;

import com.easyRepair.tabEntity.UserLoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 Created by sean on 2016/11/16. */
public interface UserLoginInfoDao extends JpaRepository<UserLoginInfo, Long>, JpaSpecificationExecutor<UserLoginInfo> {

}
