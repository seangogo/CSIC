package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Table(name = "t_news")
@Entity
public class News extends IdEntity {
    //新闻标题 新闻描述 新闻内容 新闻图片 来源
    private String newsTitle,newsDesc,newsContent,newsImg,source;

    //创建时间 注册时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime,updateTime;

    //排序
    private Long sort;

    //展示
    private boolean isShow;

    //新闻分类
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "news_type_id")
    private NewsType newsType;
    
    
    public String getNewsTitle() {
        return newsTitle;
    }
    
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    
    public String getNewsDesc() {
        return newsDesc;
    }
    
    public void setNewsDesc(String newsDesc) {
        this.newsDesc = newsDesc;
    }

    public String getNewsContent() {
        return newsContent;
    }
    
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
    
    public String getNewsImg() {
        return newsImg;
    }
    
    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Long getSort() {
        return sort;
    }
    
    public void setSort(Long sort) {
        this.sort = sort;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {

        this.source = source;
    }
    
    public NewsType getNewsType() {
        return newsType;
    }
    
    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }
}
