package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.tabEntity.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/24. */
public interface ServiceTypeService extends ICommonService<ServiceType> {
    List<ServiceType> findByServiceAppointedType_id(Long serviceAppointedTypeId);

    Page<ServiceType> page(Map<String, Object> searchParams, PageRequest id);

    List<ServiceType> findAllByIds(long[] ids);

    List<ServiceType> findByAppointedIds(long[] arrayId);

    List<Long> findTypeIdsByAreaId(Long id);

    List<ServiceType> findByAreaId(Long id);

    void createServiceTypeArea(ServiceType serviceType, Long serviceAreaId);

    List<ServiceType> getByIds(long[] ids);

    List<TreeNode> findServiceTypeTree();
}
