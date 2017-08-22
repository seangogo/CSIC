package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "t_user_info")
public class UserInfo extends IdEntity {
    private Integer score;//积分
    private float money ;//账户金额
    private String wechat;//第三方token
    private String qq;
    private String sina;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//注册时间
    @NotBlank(message = "这个字段必传")
    private String nickName;//昵称
    @NotEmpty(message = "<居住地不为空>")
    private String address;//居住地
    private String photo;//头像
    private String photoThu;
    private boolean gender;//性别
    private Integer honor;//星级
    private String invitationCode;//邀请码
    private Integer fansCount;//粉丝数
    @Email
    private String email;//邮箱


    
    public UserInfo(Integer score, float money, Date createTime, String photo, String photoThu, boolean gender, Integer honor, String invitationCode, Integer fansCount) {
        this.score = score;
        this.money = money;
        this.createTime = createTime;
        this.photo = photo;
        this.photoThu = photoThu;
        this.gender = gender;
        this.honor = honor;
        this.invitationCode = invitationCode;
        this.fansCount = fansCount;
    }
    
    public UserInfo() {
    }

    /**
     * 完善个人信息
     * @param nickName
     * @param address
     * @param gender
     * @param email
     */
    public UserInfo(String nickName, String address, boolean gender, String email) {
        this.nickName = nickName;
        this.address = address;
        this.gender = gender;
        this.email = email;
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
    
    public String getWechat() {
        return wechat;
    }
    
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }
    
    public String getQq() {
        return qq;
    }
    
    public void setQq(String qq) {
        this.qq = qq;
    }
    
    public String getSina() {
        return sina;
    }
    
    public void setSina(String sina) {
        this.sina = sina;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
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
    
    public boolean isGender() {
        return gender;
    }
    
    public void setGender(boolean gender) {
        this.gender = gender;
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
}
