package com.easyRepair.service.impl;

import com.easyRepair.dao.CouponDao;
import com.easyRepair.pojo.CouponListModule;
import com.easyRepair.service.CouponService;
import com.easyRepair.service.commons.DataTableFactory;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Coupon;
import com.easyRepair.tabEntity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/11/25. */
@Service
@Transactional(readOnly = true)
public class CouponServiceImpl implements CouponService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private CouponDao couponDao;
    
    public List<Coupon> findAll() {
        return null;
    }
    
    public Page<Coupon> find(int i, int i1) {
        return null;
    }
    
    public Page<Coupon> find(int i) {
        return null;
    }
    
    public Coupon getById(long l) {
        return couponDao.findOne(l);
    }
    
    public Coupon deleteById(long l) {
        return null;
    }
    
    public Coupon create(Coupon coupon) {
        return null;
    }

    @Transactional
    public Coupon update(Coupon coupon) {
        return couponDao.saveAndFlush(coupon);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1 = new ArrayList<Long>();
        for (long l : longs) {
            longs1.add(l);
        }
        couponDao.deleteByIds(longs1);
    }
    
    public Page<Coupon> findpageByTime(Pageable pageable) {
        return couponDao.findByTime(new Date(),pageable);
    }
    
    public Map<String,Object> page(Long id, Integer status, PageRequest pageRequest) {
        Page<Coupon> couponPage;
        if(status.equals(1)){
            couponPage=couponDao.pageByStatus(id,true,new Date(),pageRequest);
        }else if(status.equals(0)){
            couponPage=couponDao.pageByStatus(id,false,new Date(),pageRequest);
        }else {
            couponPage=couponDao.pageByEndTime(id,new Date(),pageRequest);
        }
        List<CouponListModule> couponListModuleList = new ArrayList<CouponListModule>();
        for(Coupon coupon : couponPage.getContent()) {
            couponListModuleList.add(CouponListModule.getCoupon(coupon));
        }
        return  DataTableFactory.fittingPojo(couponPage, couponListModuleList);
    }

    public Page<Coupon> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        filters.put("EQ_cut", new SpecificationUtil("cut", SpecificationUtil.Operator.EQ, false));

        Specification<Coupon> spec = DynamicSpecifications.bySearchFilter(filters.values(), Coupon.class);

        Page<Coupon> page = couponDao.findAll(spec, pageRequest);
        return page;
        /*String hql = "from Coupon c where c.cut=0";
        Query query = entityManager.createQuery(hql);
        int total = query.getResultList().size();
        query.setFirstResult(pageRequest.getPageNumber());
        query.setMaxResults(pageRequest.getPageSize());
        List<Coupon> coupons = query.getResultList();
        return new PageImpl<Coupon>(coupons, pageRequest, total);*/
    }

    public List<Coupon> getByIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        return couponDao.getByIds(longs);
    }
}
