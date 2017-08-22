package com.easyRepair.service;


import com.easyRepair.entityUtils.ICommonService;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.Role;
import common.core.bean.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/14. */
public interface ManagerService extends ICommonService<Manager> {
    /*所有后台管理员分页*/
    Page<Manager> page(int pageNum, int pageSize);

    /*根据后台管理员账号查询后台管理员*/
    Manager findByLoginName(String mobile);

    //通过角色ID查询后台管理员列表
    List<Manager> findByRole_Id(Long roleId);

    Page<Map<String, Object>> page(PageRequest pageRequest);

    Map<String, Object> pageResult(Map<String, Object> searchParams, PageRequest pageRequest, Integer draw);

    List<Manager> findByIds(long[] arrayId);

    //List<Manager> findByNickName(String nickName);

    Role getRoleById(Long id);

    List<Role> findRolesByIds(long[] arrayId);

    Integer findCountByNickName(String nickName);
}
