package com.easyRepair.service.impl;

import com.easyRepair.dao.UserLoginInfoDao;
import com.easyRepair.service.UserLoginInfoService;
import com.easyRepair.tabEntity.User;
import com.easyRepair.tabEntity.UserLoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/11/14. */
@Service
@Transactional(readOnly = true)
public class UserLoginInfoServiceImpl implements UserLoginInfoService {
    @Autowired
    private UserLoginInfoDao userLoginInfoDao;
    
    public List<UserLoginInfo> findAll() {
        return null;
    }
    
    public Page<UserLoginInfo> find(int i, int i1) {
        return null;
    }
    
    public Page<UserLoginInfo> find(int i) {
        return null;
    }
    
    public UserLoginInfo getById(long l) {
        return null;
    }
    
    @Transactional
    public UserLoginInfo deleteById(long l) {
        return null;
    }
    
    @Transactional
    public UserLoginInfo create(UserLoginInfo userLoginInfo) {
        return userLoginInfoDao.saveAndFlush(userLoginInfo);
    }
    
    @Transactional
    public UserLoginInfo update(UserLoginInfo userLoginInfo) {
        return userLoginInfoDao.saveAndFlush(userLoginInfo);
    }
    
    public void deleteAll(long[] longs) {
        
    }

}
