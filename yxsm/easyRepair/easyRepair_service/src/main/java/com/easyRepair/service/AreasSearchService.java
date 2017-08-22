package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.pojo.RepairMatchingModule;
import com.easyRepair.tabEntity.AreasSearch;
import com.easyRepair.tabEntity.ServiceArea;
import common.core.bean.Result;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/25. */
public interface AreasSearchService extends ICommonService<AreasSearch> {
    //匹配工程师
    List<RepairMatchingModule> findByLngAndLatAndServiceTypeId(Double lng, Double lat, Long serviceTypeId, Integer start, Integer length);
    
    //推荐订单
    Map<String,Object> findByLngAndLatAndServiceAreaId(Double lng, Double lat, Long userId, Integer start, Integer length);
    //首页推荐订单
    List<AreasSearch> indexTop5Order(Double lng,Double lat);

    //通过对象ID和类型查询单条记录
    AreasSearch findByObjectId(Long objectId, boolean orderOrRepair);
    
    boolean deleteByObjectIdAndOr(Long objectId, boolean orderOrRepair);

    Result findByOrderlngAndLat(Long orderId, Long id);
}
