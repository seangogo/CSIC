package com.easyRepair.service.impl;

import com.easyRepair.dao.NewsDao;
import com.easyRepair.dao.NewsTypeDao;
import com.easyRepair.service.NewsService;
import com.easyRepair.service.NewsTypeService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.Manager;
import com.easyRepair.tabEntity.News;
import com.easyRepair.tabEntity.NewsType;
import com.easyRepair.tabEntity.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2017/1/24. */
@Service
@Transactional(readOnly = true)
public class NewsTypeServiceImpl implements NewsTypeService  {
    @Autowired
    private NewsTypeDao newsTypeDao;
    
    public List<NewsType> findAll() {
        return newsTypeDao.findAll();
    }
    
    public Page<NewsType> find(int i, int i1) {
        return null;
    }
    
    public Page<NewsType> find(int i) {
        return null;
    }
    
    public NewsType getById(long l) {
        return newsTypeDao.findOne(l);
    }
    
    public NewsType deleteById(long l) {
        return null;
    }
    
    public NewsType create(NewsType newsType) {
        return null;
    }

    @Transactional
    public NewsType update(NewsType newsType) {
        return newsTypeDao.saveAndFlush(newsType);
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1=new ArrayList<Long>();
        for(long l:longs){
            longs1.add(l);
        }
        newsTypeDao.deleteByIds(longs1);
    }


    public Page<NewsType> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<NewsType> spec = DynamicSpecifications.bySearchFilter(filters.values(), NewsType.class);
        Page<NewsType> page = newsTypeDao.findAll(spec, pageRequest);
        return page;
    }

}
