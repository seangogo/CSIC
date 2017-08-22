package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.TradeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 Created by sean on 2016/12/23. */
public interface TradeInfoService extends ICommonService<TradeInfo> {
    
    Page<TradeInfo> findMinePage(Long id,String type,PageRequest pageRequest);
    
    TradeInfo findByOrder_IdOrderByCreateTimeDesc(Long id);

    Page<TradeInfo> page(PageRequest pageRequest);
}
