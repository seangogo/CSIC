package com.easyRepair.dao;

import com.easyRepair.tabEntity.QualificationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 Created by sean on 2016/12/27. */
public interface QualificationImageDao extends JpaRepository<QualificationImage, Long>, JpaSpecificationExecutor<QualificationImage> {
}
