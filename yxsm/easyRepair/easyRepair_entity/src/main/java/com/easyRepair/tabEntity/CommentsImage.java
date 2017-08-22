package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "t_comments_image")
@Entity
public class CommentsImage extends IdEntity {
    /*路径*/
    private String url;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "comments_id")
    private Comments comments;
    
    
    public CommentsImage(String url, Comments comments) {
        this.url = url;
        this.comments = comments;
    }
    
    public CommentsImage() {
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Comments getComments() {
        return comments;
    }
    
    public void setComments(Comments comments) {
        this.comments = comments;
    }
}
