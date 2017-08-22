package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Role;
import common.core.bean.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 Created by sean on 2016/12/22. */
public interface RoleService extends ICommonService<Role> {
    Page<Role> page(Map<String,Object> searchParams, Pageable pageable);

    void createAndUpdate(Role role,String ids);

    Result delete(long[] id);
}
