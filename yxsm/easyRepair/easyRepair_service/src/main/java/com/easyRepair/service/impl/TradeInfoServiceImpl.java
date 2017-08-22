package com.easyRepair.service.impl;

import com.easyRepair.dao.TradeInfoDao;
import com.easyRepair.service.TradeInfoService;
import com.easyRepair.tabEntity.NewsType;
import com.easyRepair.tabEntity.TradeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

/**
 Created by sean on 2016/12/23. */
@Service
@Transactional(readOnly = true)
public class TradeInfoServiceImpl implements TradeInfoService {
    @Autowired
    private TradeInfoDao tradeInfoDao;
    @PersistenceContext
    private EntityManager em;
    
    public Page<TradeInfo> findMinePage(Long id,String type,PageRequest pageRequest) {
        if (type.equals("trades")){
            return tradeInfoDao.findTrades(id, pageRequest);
        }
        if (type.equals("myMoneyPage")){
            return tradeInfoDao.findMineMoneyPage(id, pageRequest);
        }
        return null;
    }
    
    public TradeInfo findByOrder_IdOrderByCreateTimeDesc(Long id) {
        Query query = em.createNativeQuery("select a.* from t_trade_info a  where a.order_id=?1 order by a.create_time asc LIMIT 1", TradeInfo.class);
        query.setParameter(1, id);
        List<TradeInfo> tradeInfoList = query.getResultList();
        return tradeInfoList!=null&&tradeInfoList.size() > 0 ? tradeInfoList.get(0) : null;
    }

    public Page<TradeInfo> page(PageRequest pageRequest) {
        Page<TradeInfo> page = tradeInfoDao.findAll(new Specification<TradeInfo>() {
            public Predicate toPredicate(Root<TradeInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

    public List<TradeInfo> findAll() {
        return null;
    }
    
    public Page<TradeInfo> find(int i, int i1) {
        return null;
    }
    
    public Page<TradeInfo> find(int i) {
        return null;
    }
    
    public TradeInfo getById(long l) {
        return null;
    }
    
    public TradeInfo deleteById(long l) {
        return null;
    }
    @Transactional
    public TradeInfo create(TradeInfo tradeInfo) {
        return tradeInfoDao.saveAndFlush(tradeInfo);
    }
    
    public TradeInfo update(TradeInfo tradeInfo) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
}
