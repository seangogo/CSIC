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

import com.dh.entity.News;

public interface NewsDao extends PagingAndSortingRepository<News, Long>,JpaSpecificationExecutor<News> {

	@Query("select new News(news.id,news.newsTitle) from News news order by news.createTime desc")
	List<News> findNewsTitleList();

	@Query("select news.newsTitle from News news where news.id = ?1 ")
	String findNewsTitleById(Long newsId);
	
	
}
