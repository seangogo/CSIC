/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.dh.entity.Banner;

public interface BannerDao extends PagingAndSortingRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {


	@Query("select new Banner(banner.bannerImg,banner.bannerTargetUrl, banner.bannerTitle)  from Banner banner where banner.isShow = 1 order by banner.orderBy desc")
	List<Banner> getBanner();

	@Query("select count(banner) from Banner banner where banner.isShow = 1 ")
	Integer findBannerShowCount();

	@Query("select count(banner) from Banner banner where banner.newsId = ?1")
	Integer findBannerCountByNewsId(Long newsId);

}
