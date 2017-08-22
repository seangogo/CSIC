package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity//订单匹配对应表
@Table(name = "t_areas_search")
public class AreasSearch extends IdEntity {
    @Column(length = 6, nullable = false)
    //邮编
    private String postCode;

    //0=订单,1=工程师
    private Boolean orderOrRepair;

    @Column(nullable = false)
    //对象id
    private Long objectId;
    //经纬度
    @Column(length = 20, nullable = false)
    private Double lng,lat;

    public AreasSearch() {
    }
    
    public AreasSearch(String postCode, Boolean orderOrRepair, Long objectId, Double lng, Double lat) {
        this.postCode = postCode;
        this.orderOrRepair = orderOrRepair;
        this.objectId = objectId;
        this.lng = lng;
        this.lat = lat;
    }
    
    public String getPostCode() {
        return postCode;
    }
    
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    
    public Boolean getOrderOrRepair() {
        return orderOrRepair;
    }
    
    public void setOrderOrRepair(Boolean orderOrRepair) {
        this.orderOrRepair = orderOrRepair;
    }
    
    public Long getObjectId() {
        return objectId;
    }
    
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
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
}
