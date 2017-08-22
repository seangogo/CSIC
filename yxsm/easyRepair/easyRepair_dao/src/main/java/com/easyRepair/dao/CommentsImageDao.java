package com.easyRepair.dao;

import com.easyRepair.tabEntity.CommentsImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 Created by sean on 2016/12/29. */
public interface CommentsImageDao extends JpaRepository<CommentsImage, Long>, JpaSpecificationExecutor<CommentsImage> {
}
