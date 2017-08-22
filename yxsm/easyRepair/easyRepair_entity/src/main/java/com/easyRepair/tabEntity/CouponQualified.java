package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.*;
import java.util.Date;

/**
 Created by sean on 2017/1/3. */
@Entity
@Table(name = "t_coupon_qualified")
public class CouponQualified extends IdEntity{
    
    @OneToOne
    @JoinColumn(name = "user_id")
    //领取用户
    private User user;
    //领取次数
    private Integer count;
    @OneToOne
    @JoinColumn(name = "coupon_com_id")
    //领取对象
    private CouponCom couponCom;
    
    @Temporal(TemporalType.TIMESTAMP)
    //创建时间
    private Date createTime;
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public CouponCom getCouponCom() {
        return couponCom;
    }
    
    public void setCouponCom(CouponCom couponCom) {
        this.couponCom = couponCom;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
