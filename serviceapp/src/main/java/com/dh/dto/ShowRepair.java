package com.dh.dto;

/**
 * Created by chengong on 2016/8/8.
 */
public class ShowRepair {
    private Long repairId; //订单id
    private String repairName; //工程师名称
    private String repairPhone; //工程师电话
    private String repairTypeStr; //工程师类型
    private String repairStateStr; //工程师当前状态 0空闲 1正在接单
    private String repairIco;  //工程师头像
    private String repairLocation; //工程师最后次经纬度
    private Long currentOrderCount; //工程师当前接单数
    private Long totalOrderCount; //工程师总接单数
    private String isLogin;//是否在线  1在线  0离线
    private String lastLocationTime;//最后一次上传位置时间


    public String getLastLocationTime() {
        return lastLocationTime;
    }

    public void setLastLocationTime(String lastLocationTime) {
        this.lastLocationTime = lastLocationTime;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public Long getCurrentOrderCount() {
        return currentOrderCount;
    }

    public void setCurrentOrderCount(Long currentOrderCount) {
        this.currentOrderCount = currentOrderCount;
    }

    public Long getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(Long totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public String getRepairLocation() {
        return repairLocation;
    }

    public void setRepairLocation(String repairLocation) {
        this.repairLocation = repairLocation;
    }

    public Long getRepairId() {
        return repairId;
    }

    public void setRepairId(Long repairId) {
        this.repairId = repairId;
    }

    public String getRepairName() {
        return repairName;
    }

    public void setRepairName(String repairName) {
        this.repairName = repairName;
    }

    public String getRepairPhone() {
        return repairPhone;
    }

    public void setRepairPhone(String repairPhone) {
        this.repairPhone = repairPhone;
    }

    public String getRepairTypeStr() {
        return repairTypeStr;
    }

    public void setRepairTypeStr(String repairTypeStr) {
        this.repairTypeStr = repairTypeStr;
    }

    public String getRepairStateStr() {
        return repairStateStr;
    }

    public void setRepairStateStr(String repairStateStr) {
        this.repairStateStr = repairStateStr;
    }

    public String getRepairIco() {
        return repairIco;
    }

    public void setRepairIco(String repairIco) {
        this.repairIco = repairIco;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userId\":" + repairId +
                ", \"userName\":\"" + repairName + "\"" +
                ", \"userPhone\":\"" + repairPhone + "\"" +
                ", \"repairType\":\"" + repairTypeStr + "\"" +
                ", \"repairState\":\"" + repairStateStr + "\"" +
                ", \"repairIco\":\"" + repairIco + "\"" +
                ", \"repairLocation\":\"" + repairLocation + "\"" +
                ", \"currentOrderCount\":\"" + currentOrderCount +"\"" +
                ", \"totalOrderCount\":\"" + totalOrderCount + "\""+
                ", \"isLogin\":\"" + isLogin + "\"" +
                ", \"lastLocationTime\":\"" + lastLocationTime + "\"" +
                '}';
    }
}
