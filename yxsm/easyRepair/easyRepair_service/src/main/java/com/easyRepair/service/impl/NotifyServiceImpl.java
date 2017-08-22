package com.easyRepair.service.impl;

import com.easyRepair.dao.NotifyDao;
import com.easyRepair.service.NotifyService;
import com.easyRepair.tabEntity.NewsType;
import com.easyRepair.tabEntity.Notify;
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

/**
 Created by sean on 2017/1/18. */
@Service
@Transactional(readOnly = true)
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private NotifyDao notifyDao;
    public List<Notify> findAll() {
        
        return null;
    }
    
    public Page<Notify> find(int i, int i1) {
        return null;
    }
    
    public Page<Notify> find(int i) {
        return null;
    }
    
    public Notify getById(long l) {
        return null;
    }
    
    public Notify deleteById(long l) {
        return null;
    }
    
    @Transactional
    public Notify create(Notify notify) {
        return notifyDao.save(notify);
    }
    
    public Notify update(Notify notify) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
    }

    public Page<Notify> page(PageRequest pageRequest) {
        Page<Notify> page = notifyDao.findAll(new Specification<Notify>() {
            public Predicate toPredicate(Root<Notify> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
}
