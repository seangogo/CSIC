package com.easyRepair.pojo;

import com.easyRepair.tabEntity.UserPosition;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 Created by sean on 2016/12/29. */
public class UserPositionModule {
    
    private Double lng;
    
    private Double lat;
    
    private Date createTime;
    
    private Integer type;
    
    public Double getLng() {
        return lng;
    }
    
    public void setLng(Double lng) {
        this.lng = lng;
    }
    
    public Double getLat() {
        return lat;
    }
    
    public void setLat(Double lat) {
        this.lat = lat;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Integer getType() {
        return type;
    } public UserPositionModule getModel(UserPosition userPosition) {
        UserPositionModule userPositionModule = new UserPositionModule();
        BeanUtils.copyProperties(userPosition, userPositionModule);
        return userPositionModule;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    

}
