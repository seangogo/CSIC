package com.easyRepair.service.impl;

import com.easyRepair.dao.CommentsDao;
import com.easyRepair.service.CommentsService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 Created by sean on 2016/12/26. */
@Service
@Transactional(readOnly = true)
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsDao commentsDao;
    
    public List<Comments> findAll() {
        return null;
    }
    
    public Page<Comments> find(int i, int i1) {
        return null;
    }
    
    public Page<Comments> find(int i) {
        return null;
    }
    
    public Comments getById(long l) {
        return null;
    }
    
    public Comments deleteById(long l) {
        return null;
    }
    
    @Transactional
    public Comments create(Comments comments) {
        return commentsDao.saveAndFlush(comments);
    }
    
    public Comments update(Comments comments) {
        return null;
    }

    @Transactional
    public void deleteAll(long[] longs) {
        List<Long> longs1 = new ArrayList<Long>();
        for (long l : longs) {
            longs1.add(l);
        }
        commentsDao.deleteAllByIds(longs1);
    }
    
    public Comments findByRepairId(User user) {
        List<Comments> commentses=commentsDao.findFirst1ByOrder_RepairOrderByCreateTimeDesc(user);
        return commentses.size()>0?commentses.get(0):null;
    }
    
    public Comments findByOrder_Id(Long id) {
        return commentsDao.findByOrder_Id(id);
    }
    
    public Page<Comments> findPageByRepairId(Long id, Pageable pageable) {
        Page<Comments> page = commentsDao.findPageByRepairId(id, pageable);
        for(Comments comments : page.getContent()) {
            UserInfo userInfo = comments.getOrder().getUser().getUserInfo();
            comments.setNickName(userInfo.getNickName());
            comments.setPhotoThu(userInfo.getPhotoThu());
        }
        return page;
    }

    public int findCountByRepair(User user) {
        return commentsDao.countByOrder_Repair(user);
    }

    public Page<Comments> page(Map<String, Object> searchParams, PageRequest pageRequest) {
        Map<String, SpecificationUtil> filters = SpecificationUtil.parse(searchParams);
        //filters.put("EQ_cut", new SpecificationUtil("cut", SpecificationUtil.Operator.EQ, false));
        Specification<Comments> spec = DynamicSpecifications.bySearchFilter(filters.values(), Comments.class);
        Page<Comments> page = commentsDao.findAll(spec, pageRequest);
        return page;
    }
}
