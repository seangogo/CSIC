package com.easyRepair.service.impl;

import com.easyRepair.dao.IntegralsDao;
import com.easyRepair.service.IntegralsService;
import com.easyRepair.tabEntity.Integrals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

/**
 Created by sean on 2016/12/1. */
@Service
@Transactional(readOnly = true)
public class IntegralsServiceImpl implements IntegralsService {
    @Autowired
    private IntegralsDao integralsDao;
    
    public List<Integrals> findAll() {
        return null;
    }
    
    public Page<Integrals> find(int i, int i1) {
        return null;
    }
    
    public Page<Integrals> find(int i) {
        return null;
    }
    
    public Integrals getById(long l) {
        return null;
    }
    
    public Integrals deleteById(long l) {
        return null;
    }
    
    public Integrals create(Integrals integrals) {
        return null;
    }

    @Transactional
    public Integrals update(Integrals integrals) {
        return integralsDao.saveAndFlush(integrals);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for (long l:longs){
            longs1.add(l);
        }
        integralsDao.deleteAllByIds(longs1);
    }
    
    public Page<Integrals> findByUserId(Long userId, Pageable pageable) {
        return integralsDao.findByUserId(userId, pageable);
    }

    public Page<Integrals> page(PageRequest pageRequest) {
        Page<Integrals> page = integralsDao.findAll(new Specification<Integrals>() {
            public Predicate toPredicate(Root<Integrals> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;
                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }
                if (result != null) {
                    query.where(result);
                }
                return query.getRestriction();
            }
        }, pageRequest);
        return page;

    }

    public void createOrUpdate(Integrals integrals) {

    }
}
