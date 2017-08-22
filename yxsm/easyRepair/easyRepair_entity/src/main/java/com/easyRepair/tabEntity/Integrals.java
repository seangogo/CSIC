package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_integrals")
@Entity
public class Integrals extends IdEntity {
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(length = 4, nullable = false)
    private Integer score;
    
    @Column(length = 30, nullable = false)
    private String description;
    
    /*注册时间*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    public Integrals(Long userId, Integer score, String description, Date createTime) {
        this.userId = userId;
        this.score = score;
        this.description = description;
        this.createTime = createTime;
    }
    
    public Integrals() {
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        
        this.createTime = createTime;
    }
}
