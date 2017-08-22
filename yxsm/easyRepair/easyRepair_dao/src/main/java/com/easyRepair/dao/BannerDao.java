package com.easyRepair.dao;

import com.easyRepair.tabEntity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2016/12/20. */
@SuppressWarnings("JpaQlInspection")
public interface BannerDao extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {
    
    List<Banner> findByShowsOrderBySortAsc(boolean shows);

    @Modifying
    @Query("delete from Banner b where b.id in (?1)")
    void deleteByIds(List<Long> longs1);

    @Query("select count(b.news) from Banner b where b.news.id in (?1)")
    Integer findCountByNewsIds(List<Long> longs);
}
