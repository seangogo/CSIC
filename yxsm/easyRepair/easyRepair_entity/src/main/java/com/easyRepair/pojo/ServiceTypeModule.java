package com.easyRepair.pojo;

/**
 Created by sean on 2016/11/24. */
public class ServiceTypeModule {
    private Long id;
    private String serviceName;
    private Double price;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
}
