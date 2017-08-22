package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import common.utils.ConfigUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_service_appointed_type")
@Entity
public class ServiceAppointedType extends IdEntity {
    private String name;//一级分类名称
    @Column(length = 80)
    private String iconUrl;//服务类别图标路径

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//注册时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改时间

    public ServiceAppointedType() {
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

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIconUrl() {
        return ConfigUtil.getBaseUrl(iconUrl);
    }
    
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}