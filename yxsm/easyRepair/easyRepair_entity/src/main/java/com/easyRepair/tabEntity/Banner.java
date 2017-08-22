package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_banner")
public class Banner extends IdEntity {
    private String url;//轮播图路劲
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改时间
    private Long sort;//排序
    private boolean shows;//是否展示
    @OneToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "news_id")
    @JsonIgnore
    private News news;//关联的新闻对象
    private String title;//标题
    private String content;//内容

    @Transient
    private String targetUrl;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Long getSort() {
        return sort;
    }
    
    public void setSort(Long sort) {
        this.sort = sort;
    }
    
    public boolean isShows() {
        return shows;
    }
    
    public void setShows(boolean shows) {
        this.shows = shows;
    }
    
    public News getNews() {
        return news;
    }
    
    public void setNews(News news) {
        this.news = news;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTargetUrl() {
        return targetUrl;
    }
    
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
