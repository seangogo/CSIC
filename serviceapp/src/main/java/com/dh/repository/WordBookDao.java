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

import com.dh.entity.WordBook;

public interface WordBookDao extends PagingAndSortingRepository<WordBook, Long>, JpaSpecificationExecutor<WordBook> {
	
	@Query("select word.wordValue from WordBook word where word.wordIndex = ?1 and word.type = ?2 ")
	String findByIndex(Integer key, String type);
	@Query("select word.wordValue from WordBook word where word.wordIndex = ?1 and word.type = ?2 and word.id <> ?3 ")
	String findByIndex(Integer key, String type,Long id);

	@Query("select new WordBook(word.wordIndex,word.type,word.wordValue) from WordBook word where word.type = ?1 order by word.orderBy desc ")
	List<WordBook> findByType(String type);

}
