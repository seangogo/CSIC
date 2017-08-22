/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_word_book")
public class WordBook extends IdEntity {
	private Integer wordIndex; // 字典key
	private String type; // 字典类型
	private String wordValue; // 字典 对应 key的值
	private Long orderBy; // 排序 越大越靠前
	private Long parentId; // 父级id
	private String description; // 描述
	private User updater; // 修改人 user_id
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateTime; // 最后次修改时间

	
	
	public Integer getWordIndex() {
		return wordIndex;
	}

	public void setWordIndex(Integer wordIndex) {
		this.wordIndex = wordIndex;
	}

	public String getWordValue() {
		return wordValue;
	}

	public void setWordValue(String wordValue) {
		this.wordValue = wordValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "updater")
	public User getUpdater() {
		return updater;
	}

	public void setUpdater(User updater) {
		this.updater = updater;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public WordBook(Integer index, String type, String value) {
		this.wordIndex = index;
		this.type = type;
		this.wordValue = value;
	}
	
	public WordBook() {
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}