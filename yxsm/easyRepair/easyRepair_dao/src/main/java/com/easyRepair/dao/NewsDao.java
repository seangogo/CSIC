package com.easyRepair.dao;

import com.easyRepair.tabEntity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 Created by sean on 2017/1/24. */
public interface NewsDao extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {
    @SuppressWarnings("JpaQlInspection")
    @Query(value = "select entity from News as entity where entity.newsType.id=?1")
   // @Query(value = "select * from t_news  where news_type_id=?1",countQuery = "select count(*) from t_news  where news_type_id=?1",nativeQuery = true)
    Page<News> findPageByNewsType(Long newsTypeId, Pageable pageable);

    @Modifying
    @Query("delete from News n where n.id in (?1)")
    void deleteByIds(List<Long> longs);

    @Query("select n from News n where n.isShow=1")
    List<News> findByIsShow();
}
