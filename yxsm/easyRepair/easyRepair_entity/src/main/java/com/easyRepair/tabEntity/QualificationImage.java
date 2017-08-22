package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 Created by sean on 2016/12/27. */
@Entity
@Table(name = "t_qualification_image")
public class QualificationImage extends IdEntity {
    //路径
    private String url;
    
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    
    @ManyToOne
    @JoinColumn(name = "userRepairInfo_id")
    private UserRepairInfo userRepairInfo;
    
    public QualificationImage(String url, Date createTime, UserRepairInfo userRepairInfo) {
        this.url = url;
        this.createTime = createTime;
        this.userRepairInfo = userRepairInfo;
    }
    
    public QualificationImage() {
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public UserRepairInfo getUserRepairInfo() {
        return userRepairInfo;
    }
    
    public void setUserRepairInfo(UserRepairInfo userRepairInfo) {
        this.userRepairInfo = userRepairInfo;
    }
}
