package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_service_type")
@JsonRootName("myPojo")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ServiceType extends IdEntity {
    
    @Column(length = 10, nullable = false)
    private String serviceName;
    
    private Double price;
    @ManyToOne
    @JoinColumn(name = "appointed_type_id")
    @JsonIgnore
    private ServiceAppointedType serviceAppointedType;
    
    private Double expenses;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//注册时间

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改时间


    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getExpenses() {
        return expenses;
    }
    
    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
    
    public ServiceAppointedType getServiceAppointedType() {
        return serviceAppointedType;
    }
    
    public void setServiceAppointedType(ServiceAppointedType serviceAppointedType) {
        this.serviceAppointedType = serviceAppointedType;
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
}
