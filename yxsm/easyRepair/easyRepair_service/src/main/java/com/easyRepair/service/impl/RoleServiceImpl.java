package com.easyRepair.service.impl;

import com.easyRepair.dao.RoleDao;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.ManagerService;
import com.easyRepair.service.RoleService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Resource;
import com.easyRepair.tabEntity.Role;
import com.easyRepair.tabEntity.User;
import common.core.bean.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 Created by sean on 2016/12/22. */
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ManagerService managerService;
    
    public List<Role> findAll() {
        return roleDao.findAll();
    }
    
    public Page<Role> find(int i, int i1) {
        return null;
    }
    
    public Page<Role> find(int i) {
        return null;
    }
    
    public Role getById(long l) {
        return roleDao.findOne(l);
    }
    @Transactional
    public Role deleteById(long l) {
         roleDao.delete(l);
        return null;
    }
    @Transactional
    public Role create(Role role) {
        return roleDao.saveAndFlush(role);
    }
    
    public Role update(Role role) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }

    public Page<Role> page(Map<String,Object> searchParams,Pageable pageable) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<Role> spec = DynamicSpecifications.bySearchFilter(filters.values(), Role.class);
        Page<Role> page= roleDao.findAll(spec, pageable);
        return page;
    }


    @Transactional
    public void createAndUpdate(Role role, String ids) {
        role.setUpdateTime(new Date());
        roleDao.saveAndFlush(role);
        String[] resourceIds = ids.split(",");

        List<Resource> resourceList = new ArrayList<Resource>();
        for (String s : resourceIds) {
            Resource resource = new Resource();
            resource.setId(Long.parseLong(s));
            resourceList.add(resource);
        }
        role.setResouces(resourceList);
        roleDao.save(role);
    }

    /**
     * 删除单个角色
     * @param ids 角色Id
     */
    @Transactional
    public Result delete(long[] ids) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : ids) {
            if (managerService.findByRole_Id(l).size() > 0) {
                return new Result(false).msg("删除失败,请先清空属于该角色的所有人员");
            }
        }
        for (long l : ids) {
            longs.add(l);
        }
        //roleDao.delete(id);
        roleDao.deleteByIds(longs);
        return new Result(true).msg("删除成功");
    }

}
