/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerDao extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner> {
    /*@Query("select new Banner(banner.bannerImg,banner.bannerTargetUrl,banner.bannerTitle)  from Banner banner where banner.isShow = 1 order by banner.orderBy desc")
    public List<Banner> getBanner();*/
    @Query("select b from Banner b where b.isShow=?1 order by b.orderBy desc")
    List<Banner> getBanner(Integer isShow);
}
