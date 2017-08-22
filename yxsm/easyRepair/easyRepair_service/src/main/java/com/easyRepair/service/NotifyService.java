package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Notify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 Created by sean on 2017/1/18. */
public interface NotifyService extends ICommonService<Notify> {
    Page<Notify> page(PageRequest pageRequest);
}
