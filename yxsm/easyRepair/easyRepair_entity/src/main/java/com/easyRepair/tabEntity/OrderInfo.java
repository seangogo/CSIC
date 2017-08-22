package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity//订单详情表
@Table(name = "t_order_info")
public class OrderInfo extends IdEntity {
    //是否评价
    private boolean isComment;
    //付款方式,0=微信,1=支付宝 2=账户支付
    private String payMode;
    //上门地址
    private String address;
    //联系方式
    private String mobile;
    //联系人
    private String consignee;
    /*  @ApiModelProperty(value = "发票抬头")
      private String invoice;
      @ApiModelProperty(value = "是否开发票 0是 1否", required = true)
      private boolean isInvoice;*/
    //消耗的积分数
    private Integer score;
    //取消订单说明
    private String cancelRemark;
    
    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    
    public OrderInfo() {
    }
    
    public OrderInfo(String address, String mobile, String consignee, Integer score, Date createTime) {
        this.address = address;
        this.mobile = mobile;
        this.consignee = consignee;
        this.score = score;
        this.createTime = createTime;
    }
    
    public String getPayMode() {
        return payMode;
    }
    
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getConsignee() {
        return consignee;
    }
    
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
    
    public boolean isComment() {
        return isComment;
    }
    
    public void setComment(boolean comment) {
        isComment = comment;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public String getCancelRemark() {
        return cancelRemark;
    }
    
    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
