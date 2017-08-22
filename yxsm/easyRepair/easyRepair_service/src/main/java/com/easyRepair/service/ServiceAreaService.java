package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.tabEntity.ServiceArea;
import com.easyRepair.tabEntity.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/22. */
public interface ServiceAreaService extends ICommonService<ServiceArea> {
    Page<ServiceArea> page(Map<String, Object> searchParams, PageRequest id);

    List<ServiceArea> findByTypeId(Long id);

    /**
     * 通过ids查找服务领域集合
     * @param ids
     * @return
     */
    List<ServiceArea> findByIdIn(String[] ids);

    List<TreeNode> findServiceAreaTree();


}
