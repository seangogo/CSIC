package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_comments")
public class Comments extends IdEntity {
    private String content;//评论内容
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;//所属订单
    private Boolean state;//是否审核
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//评论时间
    private Integer service_star,speed_star,technology_star;//服务星级 上门速度星级 技术星级
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "comments", fetch = FetchType.EAGER)
    private Set<CommentsImage> commentsImages;//评论图片
    @Transient
    private String photoThu,nickName;//临时字段 头像 昵称

    public Comments(String content, Order order, Boolean state, Date createTime, Integer service_star, Integer speed_star, Integer technology_star) {
        this.content = content;
        this.order = order;
        this.state = state;
        this.createTime = createTime;
        this.service_star = service_star;
        this.speed_star = speed_star;
        this.technology_star = technology_star;
    }
    
    public Comments() {
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Boolean getState() {
        return state;
    }
    
    public void setState(Boolean state) {
        this.state = state;
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
    
    public Set<CommentsImage> getCommentsImages() {
        return commentsImages;
    }
    
    public void setCommentsImages(Set<CommentsImage> commentsImages) {
        this.commentsImages = commentsImages;
    }
    
    public String getPhotoThu() {
        return photoThu;
    }
    
    public void setPhotoThu(String photoThu) {
        this.photoThu = photoThu;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
