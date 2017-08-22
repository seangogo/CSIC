package com.server.domain.entity;

import com.server.domain.IdEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "original_message")
public class OriginalMessage extends IdEntity {
    //发送该报文的IP地址
    @Column(nullable = false, updatable = false)
    private String resourceIp;
    //二进制报文
    @Lob
    @Column(nullable = false, updatable = false)
    private byte[] originalCode;
    //保存时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createTime;

    public String getResourceIp() {
        return resourceIp;
    }

    public void setResourceIp(String resourceIp) {
        this.resourceIp = resourceIp;
    }

    public byte[] getOriginalCode() {
        return originalCode;
    }

    public void setOriginalCode(byte[] originalCode) {
        this.originalCode = originalCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
