package com.easyRepair.service.impl;

import com.easyRepair.dao.BannerDao;
import com.easyRepair.service.BannerService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Banner;
import com.easyRepair.tabEntity.NewsType;
import com.easyRepair.tabEntity.OperationLog;
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
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/12/20. */
@Service
@Transactional(readOnly = true)
public class BannerServiceImpl implements BannerService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private BannerDao bannerDao;

    public List<Banner> findAll() {
        return null;
    }
    
    public Page<Banner> find(int i, int i1) {
        return null;
    }
    
    public Page<Banner> find(int i) {
        return null;
    }
    
    public Banner getById(long l) {
        return bannerDao.findOne(l);
    }
    
    public Banner deleteById(long l) {
        return null;
    }
    
    public Banner create(Banner banner) {
        return null;
    }

    @Transactional
    public Banner update(Banner banner) {
        return bannerDao.saveAndFlush(banner);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1 = new ArrayList<Long>();
        for (long l : longs) {
            longs1.add(l);
        }
        bannerDao.deleteByIds(longs1);
    }
    
    public List<Banner> findIsShowBanner(boolean shows) {
        List<Banner> banners = bannerDao.findByShowsOrderBySortAsc(shows);
        for(Banner banner : banners) {
            banner.setTargetUrl("targerUrl" + banner.getNews().getId());
        }
        return banners;
    }

    public Page<Banner> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        filters.put("EQ_shows", new SpecificationUtil("shows", SpecificationUtil.Operator.EQ, true));
        Specification<Banner> spec = DynamicSpecifications.bySearchFilter(filters.values(), Banner.class);
        Page<Banner> page = bannerDao.findAll(spec, pageRequest);
        return page;
        /*String hql = "from Banner b where b.shows=1 ";
        Query query = entityManager.createQuery(hql);
        query.setFirstResult(pageRequest.getPageNumber());
        query.setMaxResults(pageRequest.getPageSize());
        List<Banner> banners = query.getResultList();
        return new PageImpl<Banner>(banners, pageRequest, banners.size());*/
    }

    public Integer findCountByNewsIds(long[] arrayId) {
        List<Long> longs = new ArrayList<Long>();
        for (long l : arrayId) {
            longs.add(l);
        }
        return bannerDao.findCountByNewsIds(longs);
    }
}
