package com.server.domain.entity;

import com.server.domain.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "DeviceProperty")
public class DeviceProperty extends IdEntity {
    //所属设备编号
    @Column(updatable = false, unique = true, length = 10)
    private String deviceKey;
    //属性编号
    @Column(updatable = false, length = 10)
    private String propertyKey;

    //设备名称
    @Column(updatable = false, length = 30)
    private String deviceName;//todo 方便编辑数据生产环境删除该字段

    //对应发送报文的服务器的枚举下标
    @Column(updatable = false, length = 3)
    private int udpServerIndex;

    //读报文排序字段
    @Column(updatable = false, length = 3)
    private Integer readSort;


    //关联对应页面
    @Column(length = 30, updatable = false)
    private String clientId;

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getUdpServerIndex() {
        return udpServerIndex;
    }

    public void setUdpServerIndex(int udpServerIndex) {
        this.udpServerIndex = udpServerIndex;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public Integer getReadSort() {
        return readSort;
    }

    public void setReadSort(Integer readSort) {
        this.readSort = readSort;
    }

}
