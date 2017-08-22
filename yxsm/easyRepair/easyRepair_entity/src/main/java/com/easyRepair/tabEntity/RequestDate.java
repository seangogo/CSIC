package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 Created by sean on 2017/1/16. */
@Entity
@Table(name = "t_request_date")
public class RequestDate  extends IdEntity{
        
    //订单ID
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    //发送请求用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    //类型 0抢单 1 匹配
    private Boolean matching;
    
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    
    public RequestDate() {
    }
    
    public RequestDate(Order order, User user, Boolean matching, Date createTime) {
        this.order = order;
        this.user = user;
        this.matching = matching;
        this.createTime = createTime;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Boolean getMatching() {
        return matching;
    }
    
    public void setMatching(Boolean matching) {
        this.matching = matching;
    }
}
