package com.easyRepair.service.impl;

import com.easyRepair.dao.FeedbackDao;
import com.easyRepair.service.FeedbackService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Feedback;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.OperationLog;
import com.easyRepair.tabEntity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/12/1. */
@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackDao feedbackDao;
    
    public List<Feedback> findAll() {
        return null;
    }
    
    public Page<Feedback> find(int i, int i1) {
        return null;
    }
    
    public Page<Feedback> find(int i) {
        return null;
    }
    
    public Feedback getById(long l) {
        return null;
    }
    
    public Feedback deleteById(long l) {
        return null;
    }
    
    @Transactional
    public Feedback create(Feedback feedback) {
        return feedbackDao.save(feedback);
    }
    
    @Transactional
    public Feedback update(Feedback feedback) {
        return feedbackDao.save(feedback);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for (long l:longs){
            longs1.add(l);
        }
        feedbackDao.deleteAllByIds(longs1);
    }


    public Page<Feedback> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<Feedback> spec = DynamicSpecifications.bySearchFilter(filters.values(), Feedback.class);
        Page<Feedback> page = feedbackDao.findAll(spec, pageRequest);
        return page;
    }
}
