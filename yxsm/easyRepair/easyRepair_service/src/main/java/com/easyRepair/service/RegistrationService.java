package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.pojo.RegistrationModule;
import com.easyRepair.tabEntity.Registration;

import java.util.List;

/**
 Created by sean on 2016/11/24. */
public interface RegistrationService extends ICommonService<Registration> {
    List<Registration> getRegistrationByuserId(Long userId);
    
    List<RegistrationModule> getRegistrationByTime(String lastDate,Long userId);
    
    List<RegistrationModule> executeRegistration(Long id);
    
    boolean isExecuteRegistration(Long id);
}
