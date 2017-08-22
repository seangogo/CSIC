package com.easyRepair.service.impl;

import com.easyRepair.dao.QualificationImageDao;
import com.easyRepair.service.QualificationImageService;
import com.easyRepair.tabEntity.QualificationImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 Created by sean on 2016/12/27. */
@Service
@Transactional(readOnly = true)
public class QualificationImageServiceImpl implements QualificationImageService {
    @Autowired
    private QualificationImageDao qualificationImageDao;
    
    public List<QualificationImage> findAll() {
        return null;
    }
    
    public Page<QualificationImage> find(int i, int i1) {
        return null;
    }
    
    public Page<QualificationImage> find(int i) {
        return null;
    }
    
    public QualificationImage getById(long l) {
        return null;
    }
    
    public QualificationImage deleteById(long l) {
        return null;
    }
    @Transactional
    public QualificationImage create(QualificationImage qualificationImage) {
        return qualificationImageDao.save(qualificationImage);
    }
    @Transactional
    public QualificationImage update(QualificationImage qualificationImage) {
        return qualificationImageDao.save(qualificationImage);
    }
    
    public void deleteAll(long[] longs) {
        
    }
}
