package com.easyRepair.tabEntity;


import com.easyRepair.pojo.IdEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_user_coupon")
public class UserCoupon extends IdEntity{
  
  //用户对象
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  
  @OneToOne
  @JoinColumn(name = "coupon_id")
  private Coupon coupon;
  
 //状态，0=未使用，1=已使用，默认为0
  private Boolean yesUse;
  
 //创建时间
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;
  
  public UserCoupon(User user, Coupon coupon, Boolean yesUse, Date createTime) {
    this.user = user;
    this.coupon = coupon;
    this.yesUse = yesUse;
    this.createTime = createTime;
  }
  
  public UserCoupon() {
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public Coupon getCoupon() {
    return coupon;
  }
  
  public void setCoupon(Coupon coupon) {
    this.coupon = coupon;
  }
  
  public Boolean getYesUse() {
    return yesUse;
  }
  
  public void setYesUse(Boolean yesUse) {
    this.yesUse = yesUse;
  }
  
  public Date getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
}
