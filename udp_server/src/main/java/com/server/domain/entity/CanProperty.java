package com.server.domain.entity;

import java.util.Arrays;

/**
 * 设备所有can状态
 */
public class CanProperty  {
    //canID
    private float canId;
    //can属性
    private byte[] property;
    //修改时间


    public float getCanId() {
        return canId;
    }

    public void setCanId(float canId) {
        this.canId = canId;
    }

    public byte[] getProperty() {
        return property;
    }

    public void setProperty(byte[] property) {
        this.property = property;
    }

    @Override
    public String toString() {
        return "CanProperty{" +
                "canId=" + canId +
                ", property=" + Arrays.toString(property) +
                '}';
    }
}
