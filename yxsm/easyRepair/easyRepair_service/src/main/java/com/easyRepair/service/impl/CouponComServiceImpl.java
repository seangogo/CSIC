package com.easyRepair.service.impl;

import com.easyRepair.dao.CouponComDao;
import com.easyRepair.service.CouponComService;
import com.easyRepair.service.CouponQualifiedService;
import com.easyRepair.service.UserCouponService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/3. */
@Service
@Transactional(readOnly = true)
public class CouponComServiceImpl implements CouponComService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private CouponComDao couponComDao;
    @Autowired
    private CouponQualifiedService couponQualifiedService;
    @Autowired
    private UserCouponService userCouponService;
    
    public List<CouponCom> findAll() {
        return couponComDao.findAll();
    }
    
    public Page<CouponCom> find(int i, int i1) {
        return null;
    }
    
    public Page<CouponCom> find(int i) {
        return null;
    }
    
    public CouponCom getById(long l) {
        return couponComDao.findOne(l);
    }
    
    public CouponCom deleteById(long l) {
        return null;
    }
    
    public CouponCom create(CouponCom couponCom) {
        return null;
    }

    @Transactional
    public CouponCom update(CouponCom couponCom) {
        return couponComDao.saveAndFlush(couponCom);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();

        for (long l:longs){
            longs1.add(l);
        }
        couponComDao.deleteAllByIds(longs1);
    }
    @Transactional
    public UserCoupon doleCoupon(CouponQualified couponQualified, Long userId, Coupon coupon) {
        if(couponQualified.getCount()==0){
            return null;
        }
        couponQualifiedService.updateCount(couponQualified);
        //减少剩余数量
        if(coupon.getNumber()!=-1){
            couponComDao.updateNumber(coupon);
        }
        User user=new User();
        user.setId(userId);
        UserCoupon userCoupon=new UserCoupon(user,coupon,false, new Date());
        return userCouponService.create(userCoupon);
    }
    @Transactional
    public UserCoupon doleCouponAll(Long userId, Coupon coupon) {
        //减少剩余数量
        if(coupon.getNumber()!=-1){
            couponComDao.updateNumber(coupon);
        }
        User user=new User();
        user.setId(userId);
        UserCoupon userCoupon=new UserCoupon(user,coupon,false, new Date());
        return userCouponService.create(userCoupon);
    }

    public Page<CouponCom> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        filters.put("EQ_cut", new SpecificationUtil("cut", SpecificationUtil.Operator.EQ, false));
        Specification<CouponCom> spec = DynamicSpecifications.bySearchFilter(filters.values(), CouponCom.class);
        Page<CouponCom> page = couponComDao.findAll(spec, pageRequest);
        return page;
        /*String hql = "from CouponCom where cut=0";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(pageRequest.getPageNumber());
        query.setMaxResults(pageRequest.getPageSize());
        List<CouponCom> couponComs = query.getResultList();
        return new PageImpl<CouponCom>(couponComs, pageRequest, couponComs.size());*/
    }

    public List<CouponCom> findByIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        return couponComDao.findByIds(longs);

    }
}
