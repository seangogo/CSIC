package com.easyRepair.pojo;

/**
 Created by sean on 2017/1/22. */
public class FansMudule {
    private Long id;
    /*昵称*/
    private String nickName;
    /*荣誉点*/
    private Integer honor = 0;
    /*头像*/
    private String photo;
    private String photoThu;
    
    public FansMudule() {
    }
    
    public FansMudule(Long id, String nickName, Integer honor, String photo, String photoThu) {
        this.id = id;
        this.nickName = nickName;
        this.honor = honor;
        this.photo = photo;
        this.photoThu = photoThu;
    }
    
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
    
    public Integer getHonor() {
        return honor;
    }
    
    public void setHonor(Integer honor) {
        this.honor = honor;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public String getPhotoThu() {
        return photoThu;
    }
    
    public void setPhotoThu(String photoThu) {
        this.photoThu = photoThu;
    }
}
