package com.easyRepair.service.impl;

import com.easyRepair.dao.FansDao;
import com.easyRepair.service.FansService;
import com.easyRepair.service.commons.DynamicSpecifications;
import com.easyRepair.service.commons.SpecificationUtil;
import com.easyRepair.tabEntity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/12/19. */
@Service
@Transactional(readOnly = true)
public class FansServiceImpl implements FansService {
    @Autowired
    private FansDao fansDao;
    
    public List<Fans> findAll() {
        return fansDao.findAll();
    }
    
    public Page<Fans> find(int i, int i1) {
        return null;
    }
    
    public Page<Fans> find(int i) {
        return null;
    }
    
    public Fans getById(long l) {
        return fansDao.findOne(l);
    }
    
    @Transactional
    public Fans create(Fans fans) {
        return fansDao.save(fans);
    }
    
    @Transactional
    public Fans update(Fans fans) {
        return fansDao.saveAndFlush(fans);
    }
    
    public void deleteAll(long[] longs) {
        
    }
    
    public Page<User> findPageByFansUser(Long userId, Pageable pageable) {
        return fansDao.findPageByFansUser(userId, pageable);
    }
    
    @Transactional
    public void cancelFollowOrFollow(boolean cancel, Long fromId, Long toId) {
        Fans fans = fansDao.findByFansUser_IdAndFollowUser_Id(fromId, toId);
        //关注
        if(!cancel&&fans==null) {
            User from = new User();
            from.setId(fromId);
            User to = new User();
            to.setId(toId);
            Fans saveFans = new Fans(from, to, new Date());
            fansDao.save(saveFans);
        } else {
            if(fans != null) {
                fansDao.delete(fans);
            }
        }
    }

    public Page<Fans> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        Specification<Fans> spec = DynamicSpecifications.bySearchFilter(filters.values(), Fans.class);
        Page<Fans> page = fansDao.findAll(spec, pageRequest);
        return page;
    }

    public int findBySum(Long id) {
        return fansDao.findSum(id).size();
    }

    public boolean isFans(Long userId, Long repairId) {
        Fans fans= fansDao.findByFansUser_IdAndFollowUser_Id(userId,repairId);
        return fans!=null;
    }

    @Transactional
    public Fans deleteById(long l) {
        Fans fans = getById(l);
        fansDao.delete(l);
        return fans;
    }
}
