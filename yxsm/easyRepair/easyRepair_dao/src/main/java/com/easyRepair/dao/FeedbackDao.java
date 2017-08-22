package com.easyRepair.dao;

import com.easyRepair.tabEntity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/1. */
@SuppressWarnings("JpaQlInspection")
public interface FeedbackDao extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {

    @Modifying
    @Query("delete from Feedback d where d.id in (?1)")
    void deleteAllByIds(List<Long> longs1);
}
