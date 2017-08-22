package com.easyRepair.service.impl;

import com.easyRepair.dao.CouponQualifiedDao;
import com.easyRepair.service.CouponQualifiedService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.CouponCom;
import com.easyRepair.tabEntity.CouponQualified;
import com.easyRepair.tabEntity.OperationLog;
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
 Created by sean on 2017/1/3. */
@Service
@Transactional(readOnly = true)
public class CouponQualifiedServiceImpl implements CouponQualifiedService {
    @Autowired
    private CouponQualifiedDao couponQualifiedDao;
    public List<CouponQualified> findAll() {
        return null;
    }
    
    public Page<CouponQualified> find(int i, int i1) {
        return null;
    }
    
    public Page<CouponQualified> find(int i) {
        return null;
    }
    
    public CouponQualified getById(long l) {
        return couponQualifiedDao.findOne(l);
    }
    @Transactional
    public CouponQualified deleteById(long l) {
        CouponQualified couponQualified=getById(l);
        couponQualifiedDao.delete(couponQualified);
        return couponQualified;
    }
    
    public CouponQualified create(CouponQualified couponQualified) {
        return null;
    }
    @Transactional
    public CouponQualified update(CouponQualified couponQualified) {
        return couponQualifiedDao.saveAndFlush(couponQualified);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1 = new ArrayList<Long>();
        for (long l : longs) {
            longs1.add(l);
        }
        couponQualifiedDao.deleteByIds(longs1);
    }
    
    public List<CouponQualified> findByUser_IdAndCouponCom(Long userId, CouponCom couponCom) {
        return couponQualifiedDao.findByUser_IdAndCouponCom(userId,couponCom);
    }
    
    public void updateCount(CouponQualified couponQualified) {
        couponQualifiedDao.updateCount(couponQualified);
        
    }

    public Page<CouponQualified> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<CouponQualified> spec = DynamicSpecifications.bySearchFilter(filters.values(), CouponQualified.class);
        Page<CouponQualified> page = couponQualifiedDao.findAll(spec, pageRequest);
        return page;
    }
}
