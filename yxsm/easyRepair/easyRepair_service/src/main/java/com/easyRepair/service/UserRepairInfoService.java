package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.UserRepairInfo;
import common.core.bean.PageParam;
import common.core.bean.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/22. */
public interface UserRepairInfoService extends ICommonService<UserRepairInfo> {
    List<UserRepairInfo> findByIdIn(List<Long> ids);
    
    UserRepairInfo findByUser_Id(Long userId);
    
    //推荐工程师
    List<Map<String, String>> findByOrderBySort(Double lng, Double lat);
    
    //工程师列表分页
    Map<String, Object> findRepairPage(Double lng, Double lat, Long serviceId, String jobTitle, Integer star, PageParam pageParam);

    //工程师详情
    Result detail(Long repairId);

    Result authentication(HttpServletRequest request,Long userId, UserRepairInfo userRepairInfo);
//    工程师认证信息
    String mineAuthentication();
}
