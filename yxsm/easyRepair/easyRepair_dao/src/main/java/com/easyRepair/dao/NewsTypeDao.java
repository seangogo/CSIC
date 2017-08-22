package com.easyRepair.dao;

import com.easyRepair.tabEntity.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2017/1/24. */
public interface NewsTypeDao extends JpaRepository<NewsType, Long>, JpaSpecificationExecutor<NewsType> {

    @Modifying
    @Query("delete from NewsType n where n.id in (?1)")
    void deleteByIds(List<Long> longs1);
}
