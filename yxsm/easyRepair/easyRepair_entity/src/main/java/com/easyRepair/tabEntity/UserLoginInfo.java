package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.*;
import java.util.Date;


@Entity//用户登录详情表
@Table(name = "t_user_login_info")
public class UserLoginInfo extends IdEntity {
    private Double lastLng;//最后登录的经度
    private Double lastLat;//最后登录的纬度
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;//最后登录时间
    private boolean isLogin;//登录
    private String deviceId;//设备号
    private String deviceOs;//设备类型
    private String token;//融云token唯一标识
    @Transient
    private String distance;

    public UserLoginInfo(Double lastLng, Double lastLat, Date lastLoginTime, boolean isLogin, String deviceId, String deviceOs) {
        this.lastLng = lastLng;
        this.lastLat = lastLat;
        this.lastLoginTime = lastLoginTime;
        this.isLogin = isLogin;
        this.deviceId = deviceId;
        this.deviceOs = deviceOs;
    }

    public UserLoginInfo() {
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
