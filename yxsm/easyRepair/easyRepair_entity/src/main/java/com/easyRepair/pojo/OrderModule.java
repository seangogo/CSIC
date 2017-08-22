package com.easyRepair.pojo;

import java.util.Date;
import java.util.List;

/**
 Created by sean on 2016/12/5. */
public class OrderModule {
    private Long id,repairId;
    //订单主键 接单人ID
    private Integer status,score;
    //订单状态 消耗积分
    private String orderNum,consignee,mobile,address,nickName,repairPhone,photoThu,serviceName,remark,couponName,cancelRemark;
    //订单号,联系人,联系电话,上门地址,接单人或发单人昵称,接单人电话,头像,订单服务名称,订单备注,优惠卷名称 取消事由
    private Double servicePrice,expenses,price,payPrice,disCount,disScore;
    //订单服务价格,上门费,实付金额,支付宝实付,优惠卷抵扣金额,积分抵扣金额
    private Date appointmentTime,createTime,payTime,finishTime;//预约时间 创建时间,付款时间 成交时间
    /*图片路径*/
    private List<String> imagesUrl;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    public String getConsignee() {
        return consignee;
    }
    
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getNickName() {
        return nickName;
    }
    
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    
    public String getPhotoThu() {
        return photoThu;
    }
    
    public void setPhotoThu(String photoThu) {
        this.photoThu = photoThu;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public Double getServicePrice() {
        return servicePrice;
    }
    
    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public Date getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public String getCouponName() {
        return couponName;
    }
    
    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getPayTime() {
        return payTime;
    }
    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    
    public Date getFinishTime() {
        return finishTime;
    }
    
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
    
    public String getCancelRemark() {
        return cancelRemark;
    }
    
    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }
    
    public Double getExpenses() {
        return expenses;
    }
    
    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
    
    public Double getDisCount() {
        return disCount;
    }
    
    public void setDisCount(Double disCount) {
        this.disCount = disCount;
    }
    
    public Double getDisScore() {
        return disScore;
    }
    
    public void setDisScore(Double disScore) {
        this.disScore = disScore;
    }
    
    public List<String> getImagesUrl() {
        return imagesUrl;
    }
    
    public void setImagesUrl(List<String> imagesUrl) {
        this.imagesUrl = imagesUrl;
    }
    
    public Double getPayPrice() {
        return payPrice;
    }
    
    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }
    
    public String getRepairPhone() {
        return repairPhone;
    }
    
    public void setRepairPhone(String repairPhone) {
        this.repairPhone = repairPhone;
    }

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }


}
