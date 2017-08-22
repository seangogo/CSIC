package com.easyRepair.service.impl;

import com.easyRepair.dao.ServiceAreaDao;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.ServiceAreaService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.News;
import com.easyRepair.tabEntity.ServiceArea;
import com.easyRepair.tabEntity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/22. */
@Service
@Transactional(readOnly = true)
public class ServiceAreaServiceImpl implements ServiceAreaService {
    @Autowired
    private ServiceAreaDao serviceAreaDao;
    
    public List<ServiceArea> findAll() {
        return serviceAreaDao.findAll();
    }
    
    public Page<ServiceArea> find(int i, int i1) {
        return null;
    }
    
    public Page<ServiceArea> find(int i) {
        return null;
    }
    
    public ServiceArea getById(long l) {
        return serviceAreaDao.findOne(l);
    }
    @Transactional
    public ServiceArea deleteById(long l) {
        ServiceArea serviceArea=getById(l);
        if (serviceArea!=null){
            serviceAreaDao.delete(l);
        }
        return serviceArea;
    }
    @Transactional
    public ServiceArea create(ServiceArea serviceArea) {
        return null;
    }


    @Transactional
    public ServiceArea update(ServiceArea serviceArea) {
        return serviceAreaDao.saveAndFlush(serviceArea);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for (long l:longs){
            longs1.add(l);
        }
        serviceAreaDao.deleteAllByIds(longs1);
    }

    public Page<ServiceArea> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);

        Specification<ServiceArea> spec = DynamicSpecifications.bySearchFilter(filters.values(), ServiceArea.class);

        Page<ServiceArea> page = serviceAreaDao.findAll(spec, pageRequest);
        return page;
    }

    public List<ServiceArea> findByTypeId(Long id) {
        return serviceAreaDao.findByTypeId(id);
    }

    public List<ServiceArea> findByIdIn(String[] ids) {
        List<Long> idList=new ArrayList<Long>();
        for (String s:ids){
            idList.add(Long.parseLong(s));
        }
        return serviceAreaDao.findByIdIn(idList);
    }

    public List<TreeNode> findServiceAreaTree() {
        List<TreeNode> menuList = new ArrayList<TreeNode>();
        List<ServiceArea> serviceAreas = serviceAreaDao.findAll();
        for (ServiceArea serviceArea : serviceAreas) {
            TreeNode treeNode = new TreeNode();
            treeNode.setName(serviceArea.getName());//名称
            treeNode.setType(/*res.getHasChild().equals("1") ? "folder" :*/ "item");
            treeNode.getAdditionalParameters().setId(serviceArea.getId());
            treeNode.getAdditionalParameters().setItemSelected(false);
            treeNode.getAdditionalParameters().setPid(0l);
            menuList.add(treeNode);
        }
        return menuList;
    }

    public List<ServiceType> findByAreaId(Long id) {
        return null;
    }
}
