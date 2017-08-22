package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_coupon")
public class Coupon extends IdEntity {
  @Column(length = 10, nullable = false)
  //优惠券名称
  private String name;
  @Column(nullable = false)
  //抵扣金额 限制使用金额
  private Double deduction,price;
  //优惠券可领取数量
  @Column(nullable = false,columnDefinition="INT default -1")
  private Long number;
  @OneToOne
  @JoinColumn(name = "service_type_id")
  //服务类型ID ID为1不限
  private ServiceType serviceType;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  //创建时间 使用开始时间 使用截止时间 领取开始时间 领取截止时间 修改时间
  private Date createTime, startTime, endTime, getStartTime, getEndTime, updateTime;

  @OneToOne
  @JoinColumn(name = "coupon_com_id")
  //领券对象id  id==空  不限制
  private  CouponCom couponCom;
  //领券次数,-1不限
  private Integer frequency;
  //删除标记
  private boolean cut;
  //推送状态
  private boolean push;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
      if (name != null) {
          this.name = name;
      }

  }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
  }
  
  public Double getDeduction() {
    return deduction;
  }
  
  public void setDeduction(Double deduction) {
      if (deduction != null)
    this.deduction = deduction;
  }
  
  public Long getNumber() {
    return number;
  }
  
  public void setNumber(Long number) {
      if (number != null)
    this.number = number;
  }
  
  public ServiceType getServiceType() {
    return serviceType;
  }
  
  public void setServiceType(ServiceType serviceType) {
      if (serviceType != null)
    this.serviceType = serviceType;
  }
  
  public Double getPrice() {
    return price;
  }
  
  public void setPrice(Double price) {
      if (price != null)
    this.price = price;
  }
  
  public Date getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(Date createTime) {
      if (createTime != null)
    this.createTime = createTime;
  }
  
  public Date getStartTime() {
    return startTime;
  }
  
  public void setStartTime(Date startTime) {
      if (startTime != null)
    this.startTime = startTime;
  }
  
  public Date getEndTime() {
    return endTime;
  }
  
  public void setEndTime(Date endTime) {
      if (endTime != null)
    this.endTime = endTime;
  }
  
  public Date getGetStartTime() {
    return getStartTime;
  }
  
  public void setGetStartTime(Date getStartTime) {
      if (getStartTime != null)
    this.getStartTime = getStartTime;
  }
  
  public Date getGetEndTime() {
    return getEndTime;
  }
  
  public void setGetEndTime(Date getEndTime) {
      if (getEndTime != null)
    this.getEndTime = getEndTime;
  }
  
  public CouponCom getCouponCom() {
    return couponCom;
  }
  
  public void setCouponCom(CouponCom couponCom) {
      if (couponCom != null)
    this.couponCom = couponCom;
  }
  
  public Integer getFrequency() {
    return frequency;
  }
  
  public void setFrequency(Integer frequency) {
      if (frequency != null)
    this.frequency = frequency;
  }
  
  public boolean isCut() {
    return cut;
  }
  
  public void setCut(boolean cut) {
    this.cut = cut;
  }
  
  public boolean isPush() {
    return push;
  }
  
  public void setPush(boolean push) {
    this.push = push;
  }
}
