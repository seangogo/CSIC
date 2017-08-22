package com.easyRepair.pojo;

/**
 Created by sean on 2016/12/30. */
public class RepairListModule {
    /*主键*/
    private Long id;
    /*星级*/
    private Integer honor;
    /*当前是否忙碌*/
    private boolean is_busy;
    
    /*工年限*/
    private String jobTitle;
    
    /*个人签名*/
    private String profile;
    
    /*昵称*/
    private String nickName;
    /*头像*/
    private String photo;
    /*服务领域*/
    private String serviceName;
    /*距离*/
    private String dist;
    
    public RepairListModule() {
    }
    
    public RepairListModule(Long id, Integer honor, boolean is_busy, String jobTitle, String profile, String nickName, String photo, String serviceName, String dist) {
        this.id = id;
        this.honor = honor;
        this.is_busy = is_busy;
        this.jobTitle = jobTitle;
        this.profile = profile;
        this.nickName = nickName;
        this.photo = photo;
        this.serviceName = serviceName;
        this.dist = dist;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getHonor() {
        return honor;
    }
    
    public void setHonor(Integer honor) {
        this.honor = honor;
    }
    
    public boolean is_busy() {
        return is_busy;
    }
    
    public void setIs_busy(boolean is_busy) {
        this.is_busy = is_busy;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    
    public String getProfile() {
        return profile;
    }
    
    public void setProfile(String profile) {
        this.profile = profile;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getDist() {
        return dist;
    }
    
    public void setDist(String dist) {
        this.dist = dist;
    }
}
