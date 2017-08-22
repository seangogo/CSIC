package com.easyRepair.pojo;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 Created by sean on 2017/1/5. */
public class CommentsMudule {
    private String content;//评论内容
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//评论时间
    private Integer service_star,speed_star,technology_star;//服务星级//上门速度星级//技术星级
    private List<String> commentsImages =new ArrayList<String>();//评论图片

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
    
    public Integer getService_star() {
        return service_star;
    }
    
    public void setService_star(Integer service_star) {
        this.service_star = service_star;
    }
    
    public Integer getSpeed_star() {
        return speed_star;
    }
    
    public void setSpeed_star(Integer speed_star) {
        this.speed_star = speed_star;
    }
    
    public Integer getTechnology_star() {
        return technology_star;
    }
    
    public void setTechnology_star(Integer technology_star) {
        this.technology_star = technology_star;
    }
    
    public List<String> getCommentsImages() {
        return commentsImages;
    }
    
    public void setCommentsImages(List<String> commentsImages) {
        this.commentsImages = commentsImages;
    }
}
