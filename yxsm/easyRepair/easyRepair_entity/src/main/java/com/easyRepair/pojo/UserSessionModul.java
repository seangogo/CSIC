package com.easyRepair.pojo;


import common.utils.ConfigUtil;

import java.util.Date;

/**
 Created by sean on 2016/11/21. */
public class UserSessionModul {
    private Long id;
    //用户主键
    private String nickName,loginName,type,photo,photoThu,token,invitationCode,email,address,jobCode,deviceId,deviceOs;
    //昵称 手机号 类型 头像 头像(缩略) 融云唯一标识 身份证号 邮箱 地址 工号 设备号 设备类型
    private boolean gender,isAuthentication;
    //性别 认证
    private Integer honor,fansCount,score;
    //荣誉值 粉丝数 积分
    private float money;
    //账户余额
    private Double lastLng,lastLat;//最后定位
    private Date lastLoginTime;//最后登录时间


    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getLoginName() {
        return loginName;
    }
    
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = ConfigUtil.getBaseUrl(photo);
    }
    
    public String getPhotoThu() {
        return photoThu;
    }
    
    public void setPhotoThu(String photoThu) {
        this.photoThu = ConfigUtil.getBaseUrl(photoThu);
    }
    
    public boolean isGender() {
        return gender;
    }
    
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Integer getHonor() {
        return honor;
    }
    
    public void setHonor(Integer honor) {
        this.honor = honor;
    }
    
    public String getInvitationCode() {
        return invitationCode;
    }
    
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
    
    public Integer getFansCount() {
        return fansCount;
    }
    
    public void setFansCount(Integer fansCount) {
        this.fansCount = fansCount;
    }
    

    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getJobCode() {
        return jobCode;
    }
    
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public float getMoney() {
        return money;
    }
    
    public void setMoney(float money) {
        this.money = money;
    }
    
    public Double getLastLng() {
        return lastLng;
    }
    
    public void setLastLng(Double lastLng) {
        this.lastLng = lastLng;
    }
    
    public Double getLastLat() {
        return lastLat;
    }
    
    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    public String getDeviceId() {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getDeviceOs() {
        return deviceOs;
    }
    
    public void setDeviceOs(String deviceOs) {
        this.deviceOs = deviceOs;
    }
    
    public Date getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public boolean isAuthentication() {
        return isAuthentication;
    }
    
    public void setAuthentication(boolean authentication) {
        isAuthentication = authentication;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
}
