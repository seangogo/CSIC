package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.ServiceAppointedType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/24. */
public interface ServiceAppointedTypeService extends ICommonService<ServiceAppointedType> {
    Page<ServiceAppointedType> page(Map<String, Object> searchParams, PageRequest pageRequest);

    List<ServiceAppointedType> findAllByIds(long[] arrayId);

}
