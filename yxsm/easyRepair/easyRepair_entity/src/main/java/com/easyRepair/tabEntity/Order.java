package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity//订单表
@Table(name = "t_order")
public class Order extends IdEntity {
    //订单号
    private String orderNum;

    //发单人
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    //接单人
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "repair_id")
    private User repair;

    //订单应付金额 实付金额 上门费 优惠卷抵扣金额 积分抵扣金额"
    private Double price,realpay,expenses,disCount,disScore;


    //订单详情表
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "order_info_id")
    private OrderInfo orderInfo;

    //使用的优惠券,没使用为null
    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    //服务类型,0为其他
    @OneToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    //订单详情
    private String remark;

    //订单状态，1=已下单，2=已接单，3=预付款，4=确认支付，5=待评价，6=已完成，7=已取消
    private Integer status;

    //预约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentTime;

    //订单图片
    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Set<OrderImage> OrderImages;

    //逻辑删除
    private boolean cut;
    
    @Transient
    private AreasSearch areasSearch;
    
    public Order() {
    }
    
    public Order(Double realpay,String orderNum, User user, Double price, Double expenses, Double disCount, Double disScore, OrderInfo orderInfo, Coupon coupon, ServiceType serviceType, String remark, Integer status, Date appointmentTime, Set<OrderImage> orderImages) {
        this.realpay = realpay;
        this.orderNum = orderNum;
        this.user = user;
        this.price = price;
        this.expenses = expenses;
        this.disCount = disCount;
        this.disScore = disScore;
        this.orderInfo = orderInfo;
        this.coupon = coupon;
        this.serviceType = serviceType;
        this.remark = remark;
        this.status = status;
        this.appointmentTime = appointmentTime;
        OrderImages = orderImages;
    }
    
    public String getOrderNum() {
        return orderNum;
    }
    
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getRepair() {
        return repair;
    }
    
    public void setRepair(User repair) {
        this.repair = repair;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Double getRealpay() {
        return realpay;
    }
    
    public void setRealpay(Double realpay) {
        this.realpay = realpay;
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
    
    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
    
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    
    public Coupon getCoupon() {
        return coupon;
    }
    
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
    
    public ServiceType getServiceType() {
        return serviceType;
    }
    
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Date getAppointmentTime() {
        return appointmentTime;
    }
    
    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    public Set<OrderImage> getOrderImages() {
        return OrderImages;
    }
    
    public void setOrderImages(Set<OrderImage> orderImages) {
        OrderImages = orderImages;
    }
    
    public AreasSearch getAreasSearch() {
        return areasSearch;
    }
    
    public void setAreasSearch(AreasSearch areasSearch) {
        this.areasSearch = areasSearch;
    }
    
    public boolean isCut() {
        return cut;
    }
    
    public void setCut(boolean cut) {
        this.cut = cut;
    }
}
