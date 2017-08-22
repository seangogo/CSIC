/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.dh.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "t_code_num")
public class CodeNum extends IdEntity {
	private String codeTime;
	private Long codeNum;
	private String codeParam;

	public String getCodeTime() {
		return codeTime;
	}

	public void setCodeTime(String codeTime) {
		this.codeTime = codeTime;
	}

	public Long getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(Long codeNum) {
		this.codeNum = codeNum;
	}

	public String getCodeParam() {
		return codeParam;
	}

	public void setCodeParam(String codeParam) {
		this.codeParam = codeParam;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}