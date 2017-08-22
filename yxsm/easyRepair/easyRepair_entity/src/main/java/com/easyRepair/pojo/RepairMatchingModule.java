package com.easyRepair.pojo;

/**
 Created by sean on 2016/12/2. */
public class RepairMatchingModule {
    /*用户的ID*/
    private Long id;
    /*认证：0 否 1 是*/
    private boolean isAuthentication;
    /*用户头像*/
    private String photo;
    /*真实姓名*/
    private String realName;
    /*工作年限*/
    private String jobTitle;
    /*工号*/
    private String jobCode;
    /*距离*/
    private String dist;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public boolean isAuthentication() {
        return isAuthentication;
    }
    
    public void setAuthentication(boolean authentication) {
        isAuthentication = authentication;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String getDist() {
        return dist;
    }
    
    public void setDist(String dist) {
        this.dist = dist;
    }
    
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
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
    
}
