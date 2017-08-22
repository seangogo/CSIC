package com.easyRepair.service.impl;

import com.easyRepair.dao.NewsDao;
import com.easyRepair.service.NewsService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.News;
import com.easyRepair.tabEntity.NewsType;
import com.easyRepair.tabEntity.ServiceType;
import com.easyRepair.tabEntity.User;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/24. */
@Service
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService  {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private NewsDao newsDao;
    
    public List<News> findAll() {
        return newsDao.findAll();
    }
    
    public Page<News> find(int i, int i1) {
        return null;
    }
    
    public Page<News> find(int i) {
        return null;
    }
    
    public News getById(long l) {
        return newsDao.findOne(l);
    }
    
    public News deleteById(long l) {
        return null;
    }
    
    public News create(News news) {
        return null;
    }

    @Transactional
    public News update(News news) {
        return newsDao.saveAndFlush(news);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for(long l:longs){
            //newsDao.delete(l);
            longs1.add(l);
        }
        newsDao.deleteByIds(longs1);
    }
    
    public Page<News> findPageByNewsType(Long newsTypeId, Pageable pageable) {
        return newsDao.findPageByNewsType(newsTypeId,pageable);
    }

    public Page<News> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        filters.put("EQ_isShow", new SpecificationUtil("isShow", SpecificationUtil.Operator.EQ, true));

        Specification<News> spec = DynamicSpecifications.bySearchFilter(filters.values(), News.class);

        Page<News> page = newsDao.findAll(spec, pageRequest);
        return page;
        /*String jpql = "from News n where n.isShow=1";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult(pageRequest.getPageNumber());
        query.setMaxResults(pageRequest.getPageSize());
        List<News> newses = query.getResultList();
        return new PageImpl<News>(newses, pageRequest, newses.size());*/
    }

    public List<News> findByIsShow() {
        return newsDao.findByIsShow();
    }

}
