package com.easyRepair.service;

import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.tabEntity.Resource;
import common.core.bean.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 Created by sean on 2016/12/22. */
public interface ResourceService extends ICommonService<Resource> {
    List<String> findPermissions(List<Resource> resouces);

    List<TreeNode> findRoleResources();

    List<Resource> findRoleResourcesByRoleId(Long id);


    /*根据菜单类型查询所有菜单分页*/
    Integer findTabeType(String s);

    Page<Resource> page(PageRequest pageRequest, String type);

    void saveResource(Resource resource);

    Result batchDelete(int[] arrayId);

}
