package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user_position")
public class UserPosition extends IdEntity {
    
    /*用户*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private Double lng;
    private Double lat;
    /*插入时间*/
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    
    private Integer type;
    
    public UserPosition(User user, Double lng, Double lat, Date createTime) {
        this.user = user;
        this.lng = lng;
        this.lat = lat;
        this.createTime = createTime;
    }
    
    public UserPosition() {
    }
    
    public UserPosition(User user, Double lng, Double lat, Date createTime, Integer type) {
        this.user = user;
        this.lng = lng;
        this.lat = lat;
        this.createTime = createTime;
        this.type = type;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
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
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
}
