package com.easyRepair.pojo;

import com.easyRepair.tabEntity.Coupon;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 Created by sean on 2017/1/3. */
public class CouponListModule {
    private Long id;
    
    private String name;
    
    private int deduction;
    
    private String serviceName;
    
    private int price;
    
    private Date startTime;
    
    private Date endTime;
    
    private  String couponComName;
    
    private String priceStr;
    
    public static CouponListModule getCoupon(Coupon coupon){
        CouponListModule couponListModule=new CouponListModule();
        BeanUtils.copyProperties(coupon,couponListModule);
        couponListModule.setPrice(coupon.getPrice().intValue());
        couponListModule.setDeduction(coupon.getDeduction().intValue());
        couponListModule.setServiceName(coupon.getServiceType()==null?"无限制":coupon.getServiceType().getServiceName());
        couponListModule.setPriceStr(coupon.getPrice()==0?"立减"+coupon.getDeduction()+"元":"满"+coupon.getPrice()+"减"+coupon.getDeduction()+"元");
        couponListModule.setCouponComName(coupon.getCouponCom()==null?"所有用户":"仅限:"+coupon.getCouponCom().getName());
        return couponListModule;
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
    
    public int getDeduction() {
        return deduction;
    }
    
    public void setDeduction(int deduction) {
        this.deduction = deduction;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public String getCouponComName() {
        return couponComName;
    }
    
    public void setCouponComName(String couponComName) {
        this.couponComName = couponComName;
    }
    
    public String getPriceStr() {
        return priceStr;
    }
    
    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }
    
}
