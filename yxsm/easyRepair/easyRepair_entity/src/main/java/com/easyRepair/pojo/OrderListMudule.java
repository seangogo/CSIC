package com.easyRepair.pojo;

import com.easyRepair.tabEntity.Order;
import com.easyRepair.tabEntity.OrderImage;
import java.util.Date;

/**
 Created by sean on 2016/12/27. */
public class OrderListMudule {
    /*订单主键*/
    private Long id;
    /*接单人或发单人昵称*/
    private String name;
    /*订单状态*/
    private Integer status;
    /*订单号*/
    private String orderNum;
    /*订单服务名称*/
    private String serviceName;
    /*订单服务价格*/
    private Double servicePrice;
    /*实付金额*/
    private Double price;
    /*订单详情*/
    private String remark;
    /*订单图片第一张*/
    private String imgUrl;
    /*距离*/
    private String dist;
    /*预约时间*/
    private Date appointmentTime;
    /*创建时间*/
    private Date createTime;
    public OrderListMudule(Long id, String name, Integer status, String orderNum, String serviceName, Double servicePrice, Double price, String remark, String imgUrl) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.orderNum = orderNum;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.price = price;
        this.remark = remark;
        this.imgUrl = imgUrl;
    }
    
    public OrderListMudule(Long id, String name, Integer status, String orderNum, String serviceName, Double servicePrice, Double price, String remark, String imgUrl, Date appointmentTime, Date createTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.orderNum = orderNum;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.price = price;
        this.remark = remark;
        this.imgUrl = imgUrl;
        this.appointmentTime = appointmentTime;
        this.createTime = createTime;
    }
    
    public OrderListMudule() {
    }
    

    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public Double getServicePrice() {
        return servicePrice;
    }
    
    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    public String getImgUrl() {
        return imgUrl;
    }
    
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    
    public void setDist(String dist) {
        this.dist = dist;
    }
    
    
    public Date getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
