package com.server.domain.entity;


import com.server.domain.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 添加客户端信息，用来存放客户端的sessionid
 */
@Entity
@Table(name = "clientInfo")
public class ClientInfo extends IdEntity {
    //客户端名称
    @NotNull
    @Column(length = 20, unique = true, updatable = false)
    private String clientId;
    //是否连接
    private Short connected;
    //UUID 高位
    private Long mostsignbits;
    //UUID 低位
    private Long leastsignbits;
    //最后连接时间
    private Date lastconnecteddate;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Short getConnected() {
        return connected;
    }

    public void setConnected(Short connected) {
        this.connected = connected;
    }

    public Long getMostsignbits() {
        return mostsignbits;
    }

    public void setMostsignbits(Long mostsignbits) {
        this.mostsignbits = mostsignbits;
    }

    public Long getLeastsignbits() {
        return leastsignbits;
    }

    public void setLeastsignbits(Long leastsignbits) {
        this.leastsignbits = leastsignbits;
    }

    public Date getLastconnecteddate() {
        return lastconnecteddate;
    }

    public void setLastconnecteddate(Date lastconnecteddate) {
        this.lastconnecteddate = lastconnecteddate;
    }

}