package com.easyRepair.service.impl;

import com.easyRepair.dao.ServiceAppointedTypeDao;
import com.easyRepair.pojo.tree.TreeNode;
import com.easyRepair.service.ServiceAppointedTypeService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.News;
import com.easyRepair.tabEntity.ServiceAppointedType;
import com.easyRepair.tabEntity.ServiceArea;
import com.easyRepair.tabEntity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
public class ServiceAppointedTypeServiceImpl implements ServiceAppointedTypeService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ServiceAppointedTypeDao serviceAppointedTypeDao;
    
    public List<ServiceAppointedType> findAll() {
        return serviceAppointedTypeDao.findAll();
    }
    
    public Page<ServiceAppointedType> find(int i, int i1) {
        return null;
    }
    
    public Page<ServiceAppointedType> find(int i) {
        return null;
    }
    
    public ServiceAppointedType getById(long l) {
        return serviceAppointedTypeDao.findOne(l);
    }
    
    public ServiceAppointedType deleteById(long l) {
        return null;
    }
    
    public ServiceAppointedType create(ServiceAppointedType serviceAppointedType) {
        return serviceAppointedTypeDao.save(serviceAppointedType);
    }

    @Transactional
    public ServiceAppointedType update(ServiceAppointedType serviceAppointedType) {
        return serviceAppointedTypeDao.saveAndFlush(serviceAppointedType);
    }
    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();

        for (long l:longs){
            longs1.add(l);
        }
        serviceAppointedTypeDao.deleteAllByIds(longs1);
    }

    public Page<ServiceAppointedType> page(Map<String, Object> searchParams, PageRequest pageRequest) {

        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);

        Specification<ServiceAppointedType> spec = DynamicSpecifications.bySearchFilter(filters.values(), ServiceAppointedType.class);
        Page<ServiceAppointedType> page = serviceAppointedTypeDao.findAll(spec, pageRequest);
        return page;
    }
    @Transactional
    public List<ServiceAppointedType> findAllByIds(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for (long l:longs){
            //ServiceAppointedType serviceAppointedType= serviceAppointedTypeDao.
            longs1.add(l);
        }
        List<ServiceAppointedType> serviceAppointedTypes=serviceAppointedTypeDao.findAllByIds(longs1);
        return serviceAppointedTypes;
    }

}
