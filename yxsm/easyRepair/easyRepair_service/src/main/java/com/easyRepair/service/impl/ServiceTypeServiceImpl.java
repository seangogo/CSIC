package com.easyRepair.service.impl;

import com.easyRepair.dao.ServiceAppointedTypeDao;
import com.easyRepair.dao.ServiceTypeAreaDao;
import com.easyRepair.dao.ServiceTypeDao;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.ServiceTypeService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.*;
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
 Created by sean on 2016/11/24. */
@Service
@Transactional(readOnly = true)
public class ServiceTypeServiceImpl implements ServiceTypeService {
    @Autowired
    private ServiceTypeDao serviceTypeDao;

    @Autowired
    private ServiceAppointedTypeDao serviceAppointedTypeDao;
    @Autowired
    private ServiceTypeAreaDao serviceTypeAreaDao;
    
    public List<ServiceType> findAll() {
        return serviceTypeDao.findAll();
    }
    
    public Page<ServiceType> find(int i, int i1) {
        return null;
    }
    
    public Page<ServiceType> find(int i) {
        return null;
    }
    
    public ServiceType getById(long l) {
        return serviceTypeDao.findOne(l);
    }
    
    @Transactional
    public ServiceType deleteById(long l) {
        serviceTypeDao.delete(l);
        return getById(l);
    }
    
    @Transactional
    public ServiceType create(ServiceType serviceType) {
        return serviceTypeDao.save(serviceType);
    }

    @Transactional
    public ServiceType update(ServiceType serviceType) {
        return serviceTypeDao.saveAndFlush(serviceType);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();

        for (long l:longs){
            longs1.add(l);
        }
        serviceTypeDao.deleteAllByIds(longs1);
    }
    
    public List<ServiceType> findByServiceAppointedType_id(Long serviceAppointedTypeId) {
        return serviceTypeDao.findByServiceAppointedType_id(serviceAppointedTypeId);
    }

    public Page<ServiceType> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);

        Specification<ServiceType> spec = DynamicSpecifications.bySearchFilter(filters.values(), ServiceType.class);
        Page<ServiceType> page = serviceTypeDao.findAll(spec, pageRequest);
        return page;
    }

    @Transactional
    public List<ServiceType> findAllByIds(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for (long l:longs){
            //ServiceAppointedType serviceAppointedType= serviceAppointedTypeDao.
            longs1.add(l);
        }
        List<ServiceType> serviceTypes=serviceTypeDao.findAllByIds(longs1);
        return serviceTypes;
    }

    @Transactional
    public List<ServiceType> findByAppointedIds(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();

        for (long l:longs){
            longs1.add(l);
        }
        return serviceTypeDao.findByAppointedIds(longs1);
    }

    public List<Long> findTypeIdsByAreaId(Long id) {
        return serviceTypeDao.findTypeIdsByAreaId(id);
    }

    public List<ServiceType> findByAreaId(Long id) {
        return serviceTypeDao.findByAreaId(id);
    }

    @Transactional
    public void createServiceTypeArea(ServiceType serviceType, Long serviceAreaId) {
        ServiceTypeArea serviceTypeArea = new ServiceTypeArea();
        serviceTypeArea.setServiceAreaId(serviceAreaId);
        serviceTypeArea.setServiceTypeId(serviceType.getId());
        serviceTypeAreaDao.saveAndFlush(serviceTypeArea);
    }

    public List<TreeNode> findServiceTypeTree() {
        List<TreeNode> menuList = new ArrayList<TreeNode>();
        List<ServiceType> serviceTypes = serviceTypeDao.findAll();
        for (ServiceType serviceType : serviceTypes) {
            TreeNode treeNode = new TreeNode();
            treeNode.setName(serviceType.getServiceName());//名称
            treeNode.setType(/*res.getHasChild().equals("1") ? "folder" :*/ "item");
            treeNode.getAdditionalParameters().setId(serviceType.getId());
            treeNode.getAdditionalParameters().setItemSelected(false);
            treeNode.getAdditionalParameters().setPid(0l);
            menuList.add(treeNode);
        }
        /*List<TreeNode> treeList = new TreeNode().builderTree(menuList);*/
        return menuList;
    }

    public List<ServiceType> getByIds(long[] ids) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : ids) {
            longs.add(l);
        }
        return serviceTypeDao.getByIds(longs);
    }
}
