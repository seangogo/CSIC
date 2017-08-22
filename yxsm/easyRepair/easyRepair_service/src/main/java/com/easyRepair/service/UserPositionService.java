package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.UserPosition;
import common.core.bean.Result;
import java.util.List;

/**
 Created by sean on 2016/12/5. */
public interface UserPositionService extends ICommonService<UserPosition> {
    void saveRealTimePosition(Long id, Double lng, Double lat);
    
    UserPosition findByTimeAndUserId(Long userId);
    
    UserPosition findPositionByUserId(Long id);
    
    /*传入经纬度查询跟指定用户ID 的距离排序*/
    List<UserPosition> orderByDiskInUserId(List<Long> userIds);
    //获得最新的经纬度
    Result findByRepairLngAndLat(Long repairId, Long id);
}
