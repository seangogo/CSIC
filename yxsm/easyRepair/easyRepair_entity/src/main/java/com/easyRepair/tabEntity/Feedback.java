package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_feedback")
public class Feedback extends IdEntity {
    

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    private boolean pass;
    /*创建时间*/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTime;
    
    public Feedback() {
    }

    public Feedback(User user, String content, boolean pass, Date createTime) {
        this.user = user;
        this.content = content;
        this.pass = pass;
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public boolean isPass() {
        return pass;
    }
    
    public void setPass(boolean pass) {
        this.pass = pass;
    }
}
