/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_banner")
public class Banner extends IdEntity {
	/**
	 * banner图片url
	 */
	private String  bannerImg;
	/**
	 * banner跳转详情url
	 */
	private String  bannerTargetUrl;
	/**
	 * banner详情内容富文本
	 */
	private String detailContent;
	/**
	 * 添加时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateTime;
	
	/**
	 * 排序 
	 */
	private Long orderBy;
	
	/**
	 * 是否展示
	 */
	private Integer isShow;
	
	/**
	 * banner类型  0新闻 1外链
	 */
	private Integer bannerType;
	
	private Long newsId;//新闻id
	
	private String bannerTitle;//banner标题
	

	public Banner() {
	}

	public Banner(Long id) {
		this.id = id;
	}
	
	public Banner (String bannerImg,String bannerTargetUrl ,String bannerTitle) {
		this.bannerImg = bannerImg;
		this.bannerTargetUrl = bannerTargetUrl;
		this.bannerTitle = bannerTitle;
	}
	

	public String getBannerTitle() {
		return bannerTitle;
	}

	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}

	public String getBannerImg() {
		return bannerImg;
	}

	public void setBannerImg(String bannerImg) {
		this.bannerImg = bannerImg;
	}

	public String getBannerTargetUrl() {
		return bannerTargetUrl;
	}

	public void setBannerTargetUrl(String bannerTargetUrl) {
		this.bannerTargetUrl = bannerTargetUrl;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	
	public Integer getBannerType() {
		return bannerType;
	}

	public void setBannerType(Integer bannerType) {
		this.bannerType = bannerType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}