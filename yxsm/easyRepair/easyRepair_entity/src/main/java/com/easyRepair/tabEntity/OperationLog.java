
package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_operation_log")
/*@NamedEntityGraphs(@NamedEntityGraph(name = "User.userInfo",includeAllAttributes=true,
        attributeNodes = {@NamedAttributeNode("userInfo"),@NamedAttributeNode("userLoginInfo")}))*/
@NamedEntityGraph(name = "OperationLog.creater", attributeNodes = @NamedAttributeNode("creater"))
public class OperationLog extends IdEntity {
    private Boolean type;// 记录类型 true普通记录 false异常记录
    private String description;// 描述
    private String method;// 方法
    private String requestIp;// 请求IP
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;// 添加时间
    // JPA 基于USER_ID列的多对一关系定义
    @ManyToOne
    @JoinColumn(name = "creater")
    private Manager creater;// 操作人
    private String exceptionCode;// 异常类型
    private String exceptionDetail;// 异常详情
    private String params;// 参数

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Manager getCreater() {
        return creater;
    }

    public void setCreater(Manager creater) {
        this.creater = creater;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}