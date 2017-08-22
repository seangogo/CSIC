package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/3.
 */
@Entity
@Table(name = "t_discount")
public class Discount extends IdEntity {
    //类型 0 注册 1分享 2签到 3交易
    private Integer type;
    //参数
    private Double numOne;
    //预留字段
    private Double numTwo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//修改时间

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getNumOne() {
        return numOne;
    }

    public void setNumOne(Double numOne) {
        this.numOne = numOne;
    }

    public Double getNumTwo() {
        return numTwo;
    }

    public void setNumTwo(Double numTwo) {
        this.numTwo = numTwo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
