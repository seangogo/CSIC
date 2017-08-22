package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
@NamedEntityGraphs(@NamedEntityGraph(name = "User.userInfo",includeAllAttributes=true,
        attributeNodes = {@NamedAttributeNode("userInfo"),@NamedAttributeNode("userLoginInfo")}))
public class User extends IdEntity implements java.io.Serializable {
    private String loginName;//登录账号=手机号
    private String type;//0 用户 1工程师
    private String password;//密码
    private String salt;//盐
    private boolean isAuthentication;//认证
    private boolean isLocked;//锁定
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;//用户详情
    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name = "user_login_info_id")
    private UserLoginInfo userLoginInfo;//登录详情

    /**
     * 用户注册接口
     * @param loginName
     * @param type
     * @param password
     */
    public User(String loginName, String type, String password,UserInfo userInfo,UserLoginInfo userLoginInfo) {
        this.loginName = loginName;
        this.type = type;
        this.password = password;
        this.userInfo=userInfo;
        this.userLoginInfo=userLoginInfo;
    }

    /**
     * 查找密码
     * @param loginName
     * @param type
     * @param password
     */
    public User(String loginName, String type, String password) {
        this.loginName = loginName;
        this.type = type;
        this.password = password;
    }


    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isAuthentication() {
        return isAuthentication;
    }

    public void setAuthentication(boolean authentication) {
        isAuthentication = authentication;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }


    public UserLoginInfo getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserLoginInfo(UserLoginInfo userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }

}
