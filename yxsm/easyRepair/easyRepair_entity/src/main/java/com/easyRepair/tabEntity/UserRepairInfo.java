package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_user_repair_info")
public class UserRepairInfo extends IdEntity {
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    //身份证号码
    private String identityCode;
    @Column(length = 2)
    private Integer state;
    //认证时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //资格证书图片
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "userRepairInfo",fetch = FetchType.LAZY)
    private Set<QualificationImage> qualificationImages;
    //签名
    private String profile;
    //工年限
    private String jobTitle;
    //姓名
    @Column(length = 4)
    private String realName;
    //工号
    @Column(length = 30, nullable = false)
    private String jobCode;
    //排序
    @Column(nullable = false)
    private Integer sort;
    //忙碌
    private boolean is_busy;
    
    //serviceID 以逗号分割
    @Transient
    private String serviceId;
    
    //服务名称
    @Transient
    private String serviceName;
    
    //disk 距离当前用户的距离
    @Transient
    private Integer disk;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_user_repair_service_area", joinColumns = {@JoinColumn(name = "user_repair_info_id")}, inverseJoinColumns = {@JoinColumn(name = "service_area_id")})
    private Set<ServiceArea> serviceArea;
    
    public UserRepairInfo() {
    }

    public UserRepairInfo(User user, Integer state, String profile, String jobCode, Integer sort,Date createTime) {
        this.user = user;
        this.state = state;
        this.profile = profile;
        this.jobCode = jobCode;
        this.sort = sort;
    }


    public UserRepairInfo(String identityCode, String profile, String jobTitle, String realName) {
        this.identityCode = identityCode;
        this.profile = profile;
        this.jobTitle = jobTitle;
        this.realName = realName;
    }

    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getIdentityCode() {
        return identityCode;
    }
    
    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Set<QualificationImage> getQualificationImages() {
        return qualificationImages;
    }
    
    public void setQualificationImages(Set<QualificationImage> qualificationImages) {
        this.qualificationImages = qualificationImages;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getJobCode() {
        return jobCode;
    }
    
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }
    
    public Integer getSort() {
        return sort;
    }
    
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    public boolean is_busy() {
        return is_busy;
    }
    
    public void setIs_busy(boolean is_busy) {
        this.is_busy = is_busy;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    @JsonIgnore
    public Set<ServiceArea> getServiceArea() {
        return serviceArea;
    }
    
    @JsonIgnore
    public void setServiceArea(Set<ServiceArea> serviceArea) {
        this.serviceArea = serviceArea;
        
    }
    
    public String getServiceId() {
        StringBuffer stringBuffer = new StringBuffer();
        int i=0;
        if (this.getServiceArea()!=null){
            for(ServiceArea serviceArea : this.getServiceArea()) {
                if(i==0){
                    stringBuffer.append(serviceArea.getId());
                    i++;
                }
                stringBuffer.append(","+serviceArea.getId());
            }
            return stringBuffer.toString();
        }else{
            return this.serviceId;
        }
    }
    
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    public String getServiceName() {
        StringBuffer stringBuffer = new StringBuffer();
        for(ServiceArea serviceArea : this.getServiceArea()) {
            stringBuffer.append(serviceArea.getName() + ",");
        }
        return stringBuffer.toString();
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public Integer getDisk() {
        return disk;
    }
    
    public void setDisk(Integer disk) {
        this.disk = disk;
    }
}
