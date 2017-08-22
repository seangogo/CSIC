package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_service_area")
public class ServiceArea extends IdEntity {
    //服务领域名称
    @Column(length = 6, nullable = false)
    private String name;

    //服务类别图标路径
    @Column(length = 100)
    private String iconUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//注册时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改时间


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

    /* @
         */
 /* @JsonIgnore
  @ManyToMany(cascade = CascadeType.MERGE,fetch= FetchType.LAZY)
  @JoinTable(name = "t_user_repair_service_area", joinColumns = { @JoinColumn(name ="service_area_id" )}, inverseJoinColumns = { @JoinColumn(name = "user_repair_info_id") })
  private Set<UserRepairInfo> userRepairInfoSet;*/
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
