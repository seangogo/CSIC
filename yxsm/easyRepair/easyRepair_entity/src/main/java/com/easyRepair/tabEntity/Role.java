package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_role")
public class Role extends IdEntity {

    //角色名称
    @Column(length = 6, nullable = false)
    private String name;
     //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    
    //角色描述
    @Column(length = 50, nullable = false)
    private String description;
    
    /*模块功能集合*/
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(name = "t_role_resource", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    @OrderBy("level,sort DESC")
    @JsonIgnore
    private List<Resource> resouces = new ArrayList<Resource>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Resource> getResouces() {
        return resouces;
    }

    public void setResouces(List<Resource> resouces) {
        this.resouces = resouces;
    }
}
