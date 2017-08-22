package com.easyRepair.service.impl;

import com.easyRepair.dao.CommentsImageDao;
import com.easyRepair.service.CommentsImageService;
import com.easyRepair.tabEntity.CommentsImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/12/29. */
@Service
@Transactional(readOnly = true)
public class CommentsImageServiceImpl implements CommentsImageService {
    @Autowired
    private CommentsImageDao commentsImageDao;
    
    public List<CommentsImage> findAll() {
        return null;
    }
    
    public Page<CommentsImage> find(int i, int i1) {
        return null;
    }
    
    public Page<CommentsImage> find(int i) {
        return null;
    }
    
    public CommentsImage getById(long l) {
        return null;
    }
    
    public CommentsImage deleteById(long l) {
        return null;
    }
    
    @Transactional
    public CommentsImage create(CommentsImage commentsImage) {
        return commentsImageDao.save(commentsImage);
    }
    
    public CommentsImage update(CommentsImage commentsImage) {
        return null;
    }
    
    public void deleteAll(long[] longs) {
        
    }
}
