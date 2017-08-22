package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.ServiceTypeArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;


/**
 * Created by WJY on 2017/2/21.
 */
public interface ServiceTypeAreaService extends ICommonService<ServiceTypeArea> {
    ServiceTypeArea findByIds(Long serviceAreaId, Long serviceTypeId);


    List<ServiceTypeArea> findByServiceTypeIds(long[] arrayId);

    void deleteByServiceTypeIds(long[] arrayId);

    Page<ServiceTypeArea> page(Map<String, Object> searchParams, PageRequest pageRequest);

    void deleteByIds(long[] arrayId);

    int findCountByServiceTypeIdAndServiceAreaId(Long serviceTypeId, long l);
}
