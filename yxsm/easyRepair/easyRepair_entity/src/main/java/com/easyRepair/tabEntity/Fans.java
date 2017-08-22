package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_fans")
public class Fans extends IdEntity {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    //关注者
    private User fansUser;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    //被关注者
    private User followUser;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    public Fans(User fansUser, User followUser, Date createTime) {
        this.fansUser = fansUser;
        this.followUser = followUser;
        this.createTime = createTime;
    }
    
    public Fans(User fansUser, User followUser) {
        this.fansUser = fansUser;
        this.followUser = followUser;
    }
    
    public Fans() {
    }
    
    public User getFansUser() {
        return fansUser;
    }
    
    public void setFansUser(User fansUser) {
        this.fansUser = fansUser;
    }
    
    public User getFollowUser() {
        return followUser;
    }
    
    public void setFollowUser(User followUser) {
        this.followUser = followUser;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
