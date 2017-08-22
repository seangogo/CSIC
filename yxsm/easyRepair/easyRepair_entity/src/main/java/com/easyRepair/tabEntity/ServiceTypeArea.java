package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_service_type_area")
public class ServiceTypeArea extends IdEntity {

    private Long serviceTypeId;

    private Long serviceAreaId;
    
    public Long getServiceTypeId() {
        return serviceTypeId;
    }
    
    public void setServiceTypeId(Long serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
    
    public Long getServiceAreaId() {
        return serviceAreaId;
    }
    
    public void setServiceAreaId(Long serviceAreaId) {
        this.serviceAreaId = serviceAreaId;
    }
}
