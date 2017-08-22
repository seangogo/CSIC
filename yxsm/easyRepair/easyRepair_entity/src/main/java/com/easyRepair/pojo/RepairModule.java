package com.easyRepair.pojo;

import com.easyRepair.tabEntity.Comments;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Set;

/**
 Created by sean on 2016/12/26. */
public class RepairModule {
    //工程师主键
    private Long id;
    //昵称
    private String nickName;
    //星级
    private Integer honor;
    //资格证书编号
    private String qualification;
    //获奖证书
    private List<String> qualificationImageList;
    //个人签名
    private String profile;
    //工年限
    private String jobTitle;
    //工号
    private String jobCode;
    //状态 1 离线 2在线 3忙碌
    private String status;
    //服务领域
    private List<String> serviceName;
    //总评论数
    private Integer commentsNum;
    //最近评论
    private Comments comments;
    //fans总数
    private  Integer fansSum;
    //总接单数
    private Long orderSum;
    //手机号
    private String loginName;

    //头像
    private String photo;
    //是否关注
    private boolean isFans;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public String getJobCode() {
        return jobCode;
    }
    
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<String> getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(List<String> serviceName) {
        this.serviceName = serviceName;
    }
    
    public Integer getCommentsNum() {
        return commentsNum;
    }
    
    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Integer getFansSum() {
        return fansSum;
    }

    public void setFansSum(Integer fansSum) {
        this.fansSum = fansSum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {

        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getHonor() {
        return honor;
    }

    public void setHonor(Integer honor) {
        this.honor = honor;
    }

    public List<String> getQualificationImageList() {
        return qualificationImageList;
    }

    public void setQualificationImageList(List<String> qualificationImageList) {
        this.qualificationImageList = qualificationImageList;
    }

    public Long getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Long orderSum) {
        this.orderSum = orderSum;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public boolean isFans() {
        return isFans;
    }

    public void setFans(boolean fans) {
        isFans = fans;
    }
}
