/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.repository;

import com.dh.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordBookDao extends JpaRepository<WordBook, Integer>, JpaSpecificationExecutor<WordBook> {

    @Query("select word.wordValue from WordBook word where word.wordIndex = ?1 and word.type = ?2 ")
    String findByIndex(Integer index, String type);

    @Query("select  word from WordBook word where word.type = ?1 order by word.orderBy desc ")
    List<WordBook> findByType(String type);

}
