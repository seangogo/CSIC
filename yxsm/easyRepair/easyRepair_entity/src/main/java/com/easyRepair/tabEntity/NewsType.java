package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 Created by sean on 2017/1/24. */
@Entity
@Table(name = "t_newsType")
public class NewsType extends IdEntity{
    //名称
    private String name;
    //排序
    private Long sort;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Long getSort() {
        return sort;
    }
    
    public void setSort(Long sort) {
        this.sort = sort;
    }
}
