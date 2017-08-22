/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.easyRepair.service.commons;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Map.Entry;

public class SpecificationUtil {

    public enum Operator {
        //等于 不等于  之前  之后  LIKE 是空非空
        EQ,NEQ,BEG,AFT,LIKE,NL,NU,NN,GT,LT,GTE,LTE,JOIN
        //EQ, LIKE, GT, LT, GTE, LTE
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public SpecificationUtil(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * searchParams中key的格式为OPERATOR_FIELDNAME
     */
    public static Map<String, SpecificationUtil> parse(Map<String, Object> searchParams) {
        Map<String, SpecificationUtil> filters = Maps.newHashMap();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            // 过滤掉空值
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringUtils.isBlank((String) value)) {
                continue;
            }

            // 拆分operator与filedAttribute
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            // 创建searchFilter
            SpecificationUtil filter = new SpecificationUtil(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }
}
