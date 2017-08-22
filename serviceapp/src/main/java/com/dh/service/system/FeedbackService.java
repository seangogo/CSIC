/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.service.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.dh.common.CommonButil;
import com.dh.configure.annotation.SystemServiceLog;
import com.dh.entity.Feedback;
import com.dh.repository.FeedbackDao;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class FeedbackService {
	
	@Autowired
	private FeedbackDao feedbackDao;

	/**
	 * 分页查询意见反馈列表
	 * @param pageNumber
	 * @param pageSize
	 * @param userName 
	 * @param loginName 
	 * @return
	 */
	@SystemServiceLog(description = "查询意见反馈列表")
	public Page<Feedback> findFeedbackList(Integer pageNumber, Integer pageSize, String loginName, String userName) {
		PageRequest pageRequest = buildFeedbackListPageRequest(pageNumber, pageSize);
		Specification<Feedback> spe = buildFeedbackListSpecification(loginName,userName);
		return feedbackDao.findAll(spe,pageRequest);
	}
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildFeedbackListPageRequest(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}
	
	/**
	 * 设置查询新闻列表的条件
	 * @param loginName
	 * @param userName 
	 * @return
	 */
	private Specification<Feedback> buildFeedbackListSpecification(String loginName, String userName) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		if (!CommonButil.isEmpty(loginName)) {
			filters.put("loginName", new SearchFilter("user.loginName", Operator.LIKE, loginName));
		}
		if (!CommonButil.isEmpty(userName)) {
			filters.put("userName", new SearchFilter("user.userName", Operator.LIKE, userName));
		}
		Specification<Feedback> spec = DynamicSpecifications.bySearchFilter(filters.values(), Feedback.class);
		return spec;
	}
	
	@SystemServiceLog(description = "查询意见反馈")
	public Feedback findOne(Long id) {
		return feedbackDao.findOne(id);
	}

	@SystemServiceLog(description = "删除意见反馈")
	public void delete(Long id) {
		feedbackDao.delete(id);
	}
	
	
}
