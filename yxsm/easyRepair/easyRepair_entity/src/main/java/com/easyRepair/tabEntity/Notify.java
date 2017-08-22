package com.easyRepair.tabEntity;

import com.easyRepair.pojo.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 Created by sean on 2017/1/18. */
@Entity
@Table(name = "t_notify")
public class Notify extends IdEntity{
    //通知
    private Date notify_time;
    //通知类型
    @Column(length = 64, nullable = false)
    private String notify_type;
    //通知校验ID
    @Column(length = 128, nullable = false)
    private String notify_id;
    //支付宝分配给开发者的应用Id
    @Column(length = 32, nullable = false)
    private String app_id;
    //编码格式
    @Column(length = 10, nullable = false)
    private String charset;
    //接口版本
    @Column(length = 3, nullable = false)
    private String version;
    //签名类型
    @Column(length = 10, nullable = false)
    private String sign_type;
    //签名
    @Column(length = 256, nullable = false)
    private String sign;
    //支付宝交易号
    @Column(length = 64, nullable = false)
    private String trade_no;
    //商户订单号
    @Column(length = 64, nullable = false)
    private String out_trade_no;
    //商户业务号
    @Column(length = 64)
    private String out_biz_no;
    //买家支付宝用户号
    @Column(length = 16)
    private String buyer_id;
    //买家支付宝账号
    @Column(length = 100)
    private String buyer_logon_id;
    //卖家支付宝用户号
    @Column(length = 30)
    private String seller_id;
    //卖家支付宝账号
    @Column(length = 100)
    private String seller_email;
    //交易状态
    @Column(length = 32)
    private String trade_status;
    //订单金额
    private Double total_amount;
    //实收金额
    private Double receipt_amount;
    //开票金额
    private Double invoice_amount;
    //付款金额
    private Double buyer_pay_amount;
    //集分宝金额
    private Double point_amount;
    //总退款金额
    private Double refund_fee;
    //订单标题
    @Column(length = 256)
    private String subject;
    //商品描述
    @Column(length = 400)
    private String body;
    //创建时间 付款时间 退款时间 结束时间
    private Date gmt_create,gmt_payment,gmt_refund,gmt_close;

    //支付金额信息
    @Column(length = 512)
    private String fund_bill_list;
    //回传参数
    @Column(length = 512)
    private String passback_params;
    //优惠券信息
    private String  voucher_detail_list;
    //验签状态
    private boolean success;
    
    public Notify() {
    }
    
    public Notify(Date notify_time, String notify_type, String notify_id, String app_id, String charset, String version, String sign_type, String sign, String trade_no, String out_trade_no, String out_biz_no, String buyer_id, String buyer_logon_id, String seller_id, String seller_email, String trade_status, Double total_amount, Double receipt_amount, Double invoice_amount, Double buyer_pay_amount, Double point_amount, Double refund_fee, String subject, String body, Date gmt_create, Date gmt_payment, Date gmt_refund, Date gmt_close, String fund_bill_list, String passback_params, String voucher_detail_list, boolean success) {
        this.notify_time = notify_time;
        this.notify_type = notify_type;
        this.notify_id = notify_id;
        this.app_id = app_id;
        this.charset = charset;
        this.version = version;
        this.sign_type = sign_type;
        this.sign = sign;
        this.trade_no = trade_no;
        this.out_trade_no = out_trade_no;
        this.out_biz_no = out_biz_no;
        this.buyer_id = buyer_id;
        this.buyer_logon_id = buyer_logon_id;
        this.seller_id = seller_id;
        this.seller_email = seller_email;
        this.trade_status = trade_status;
        this.total_amount = total_amount;
        this.receipt_amount = receipt_amount;
        this.invoice_amount = invoice_amount;
        this.buyer_pay_amount = buyer_pay_amount;
        this.point_amount = point_amount;
        this.refund_fee = refund_fee;
        this.subject = subject;
        this.body = body;
        this.gmt_create = gmt_create;
        this.gmt_payment = gmt_payment;
        this.gmt_refund = gmt_refund;
        this.gmt_close = gmt_close;
        this.fund_bill_list = fund_bill_list;
        this.passback_params = passback_params;
        this.voucher_detail_list = voucher_detail_list;
        this.success = success;
    }
    
    public Date getNotify_time() {
        return notify_time;
    }
    
    public void setNotify_time(Date notify_time) {
        this.notify_time = notify_time;
    }
    
    public String getNotify_type() {
        return notify_type;
    }
    
    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }
    
    public String getNotify_id() {
        return notify_id;
    }
    
    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }
    
    public String getApp_id() {
        return app_id;
    }
    
    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }
    
    public String getCharset() {
        return charset;
    }
    
    public void setCharset(String charset) {
        this.charset = charset;
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getSign_type() {
        return sign_type;
    }
    
    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public String getTrade_no() {
        return trade_no;
    }
    
    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
    
    public String getOut_trade_no() {
        return out_trade_no;
    }
    
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
    
    public String getOut_biz_no() {
        return out_biz_no;
    }
    
    public void setOut_biz_no(String out_biz_no) {
        this.out_biz_no = out_biz_no;
    }
    
    public String getBuyer_id() {
        return buyer_id;
    }
    
    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }
    
    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }
    
    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }
    
    public String getSeller_id() {
        return seller_id;
    }
    
    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }
    
    public String getSeller_email() {
        return seller_email;
    }
    
    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }
    
    public String getTrade_status() {
        return trade_status;
    }
    
    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }
    
    public Double getTotal_amount() {
        return total_amount;
    }
    
    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }
    
    public Double getReceipt_amount() {
        return receipt_amount;
    }
    
    public void setReceipt_amount(Double receipt_amount) {
        this.receipt_amount = receipt_amount;
    }
    
    public Double getInvoice_amount() {
        return invoice_amount;
    }
    
    public void setInvoice_amount(Double invoice_amount) {
        this.invoice_amount = invoice_amount;
    }
    
    public Double getBuyer_pay_amount() {
        return buyer_pay_amount;
    }
    
    public void setBuyer_pay_amount(Double buyer_pay_amount) {
        this.buyer_pay_amount = buyer_pay_amount;
    }
    
    public Double getPoint_amount() {
        return point_amount;
    }
    
    public void setPoint_amount(Double point_amount) {
        this.point_amount = point_amount;
    }
    
    public Double getRefund_fee() {
        return refund_fee;
    }
    
    public void setRefund_fee(Double refund_fee) {
        this.refund_fee = refund_fee;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public Date getGmt_create() {
        return gmt_create;
    }
    
    public void setGmt_create(Date gmt_create) {
        this.gmt_create = gmt_create;
    }
    
    public Date getGmt_payment() {
        return gmt_payment;
    }
    
    public void setGmt_payment(Date gmt_payment) {
        this.gmt_payment = gmt_payment;
    }
    
    public Date getGmt_refund() {
        return gmt_refund;
    }
    
    public void setGmt_refund(Date gmt_refund) {
        this.gmt_refund = gmt_refund;
    }
    
    public Date getGmt_close() {
        return gmt_close;
    }
    
    public void setGmt_close(Date gmt_close) {
        this.gmt_close = gmt_close;
    }
    
    public String getFund_bill_list() {
        return fund_bill_list;
    }
    
    public void setFund_bill_list(String fund_bill_list) {
        this.fund_bill_list = fund_bill_list;
    }
    
    public String getPassback_params() {
        return passback_params;
    }
    
    public void setPassback_params(String passback_params) {
        this.passback_params = passback_params;
    }
    
    public String getVoucher_detail_list() {
        return voucher_detail_list;
    }
    
    public void setVoucher_detail_list(String voucher_detail_list) {
        this.voucher_detail_list = voucher_detail_list;
        
        
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
