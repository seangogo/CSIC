package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_coupon_com")
public class CouponCom extends IdEntity {
  
  //领券对象类型 0新用户 1订单用户 2 分享 3 最新优惠
  @Column(length = 2, nullable = false)
  private Integer type;
  
  //领券对象名
  @Column(length = 10, nullable = false)
  private String name;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  //创建时间 开始时间 结束时间
  private Date createTime,startTime,endTime;

  //限制金额
  private Double price;

  //删除标记
  private boolean cut;
  
  public CouponCom() {
  }
  
  public CouponCom(Integer type, String name, Date createTime, Date startTime, Date endTime, Double price, boolean cut) {
    this.type = type;
    this.name = name;
    this.createTime = createTime;
    this.startTime = startTime;
    this.endTime = endTime;
    this.price = price;
    this.cut = cut;
  }
  
  public Integer getType() {
    return type;
  }
  
  public void setType(Integer type) {
    this.type = type;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Date getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
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
  
  public Double getPrice() {
    return price;
  }
  
  public void setPrice(Double price) {
    this.price = price;
  }
  
  public boolean isCut() {
    return cut;
  }
  
  public void setCut(boolean cut) {
    this.cut = cut;
  }
}
